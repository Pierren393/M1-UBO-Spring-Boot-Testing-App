package com.service.impl;

import com.repository.UserDao;
import com.dto.*;
import com.entity.User;
import com.mapper.UserMapper;
import com.service.AuthenticationService;
import com.service.JwtService;
import lombok.RequiredArgsConstructor;
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
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userDao.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userDao.save(user);
        
        // On suppose que votre jwtService utilise getUsername() (Spring Security) qui renvoie notre email configuré
        String token = jwtService.generateToken(user);
        
        return AuthResponseDto.builder()
                .accessToken(token)
                .expiresIn(3600)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                
        User user = userDao.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
                
        String token = jwtService.generateToken(user);
        
        return AuthResponseDto.builder()
                .accessToken(token)
                .expiresIn(3600)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public UserDto getCurrentUser(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequestDto request, String currentUserEmail) {
        User userToUpdate = userDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
                
        User currentUser = userDao.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur courant introuvable"));

        if (!Objects.equals(userToUpdate.getId(), currentUser.getId()) && !"ADMIN".equals(currentUser.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit : vous ne pouvez modifier que votre propre compte");
        }

        userMapper.updateEntity(request, userToUpdate);
        
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            userToUpdate.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        userDao.save(userToUpdate);
        return userMapper.toDto(userToUpdate);
    }

    @Override
    public void deleteUser(Long id, String currentUserEmail) {
        User userToDelete = userDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
                
        User currentUser = userDao.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur courant introuvable"));

        if (!Objects.equals(userToDelete.getId(), currentUser.getId()) && !"ADMIN".equals(currentUser.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit");
        }
        
        userDao.delete(userToDelete);
    }
}
