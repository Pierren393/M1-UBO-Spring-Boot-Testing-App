package com.service.impl;

import com.dto.*;
import com.entity.User;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import com.service.AuthenticationService;
import com.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * Implémentation du service d'authentification et de gestion des utilisateurs.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponseDto.builder()
                .accessToken(token)
                .expiresIn(3600)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        log.info(">>>> [LOGIN DEBUG] Tentative de connexion pour : {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            log.error(">>>> [LOGIN DEBUG] Utilisateur introuvable pour email : {}", request.getEmail());
        } else {
            log.info(">>>> [LOGIN DEBUG] Utilisateur trouvé (ID={}). Test manuel du mot de passe...", user.getId());
            boolean match = passwordEncoder.matches(request.getPassword(), user.getPassword());
            log.info(">>>> [LOGIN DEBUG] Password match manual result : {}", match);
            if (!match) {
                log.error(">>>> [LOGIN DEBUG] Erreur : Le mot de passe envoyé ne correspond pas au hash en base.");
            }
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            log.info(">>>> [LOGIN DEBUG] AuthenticationManager : SUCCÈS");
        } catch (Exception e) {
            log.error(">>>> [LOGIN DEBUG] AuthenticationManager : ÉCHEC : {}", e.getMessage());
            throw e;
        }

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
        }

        String token = jwtService.generateToken(user);

        return AuthResponseDto.builder()
                .accessToken(token)
                .expiresIn(3600)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public UserDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequestDto request, String currentUserEmail) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur courant introuvable"));

        if (!Objects.equals(userToUpdate.getId(), currentUser.getId()) && !"ADMIN".equals(currentUser.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Accès interdit : vous ne pouvez modifier que votre propre compte");
        }

        userMapper.updateEntity(request, userToUpdate);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(userToUpdate);
        return userMapper.toDto(userToUpdate);
    }

    @Override
    public void deleteUser(Long id, String currentUserEmail) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur courant introuvable"));

        if (!Objects.equals(userToDelete.getId(), currentUser.getId()) && !"ADMIN".equals(currentUser.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit");
        }

        userRepository.delete(userToDelete);
    }
}
