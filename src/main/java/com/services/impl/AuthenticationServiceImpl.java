package com.services.impl;

import com.dtos.*;
import com.entities.User;
import com.mappers.UserMapper;
import com.repositories.UserRepository;
import com.services.AuthenticationService;
import com.services.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implémentation du service d'authentification.
 * Gère l'inscription, la connexion et le CRUD des utilisateurs.
 */
@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     UserMapper userMapper,
                                     JwtService jwtService,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(RegisterRequestDto registerRequest) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Un utilisateur avec cet email existe déjà");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Identifiants invalides"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Identifiants invalides");
        }

        String accessToken = jwtService.generateToken(user.getId(), user.getEmail());
        // Refresh token simplifié : même token avec expiration plus longue
        String refreshToken = jwtService.generateToken(user.getId(), user.getEmail());

        return new AuthResponseDto(accessToken, refreshToken, jwtService.getExpiration(), "Bearer");
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUser(String token) {
        String email = jwtService.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("L'utilisateur avec l'ID %d n'existe pas", id)));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequestDto updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("L'utilisateur avec l'ID %d n'existe pas", id)));

        if (updateRequest.getEmail() != null) {
            // Vérifier que le nouvel email n'est pas déjà pris par un autre user
            if (!user.getEmail().equals(updateRequest.getEmail())
                    && userRepository.existsByEmail(updateRequest.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé");
            }
            user.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getUsername() != null) {
            user.setUsername(updateRequest.getUsername());
        }
        if (updateRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("L'utilisateur avec l'ID %d n'existe pas", id));
        }
        userRepository.deleteById(id);
    }
}
