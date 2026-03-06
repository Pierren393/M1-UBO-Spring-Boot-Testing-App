package com.service.impl;

import com.dto.*;
import com.entity.User;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import com.service.AuthenticationService;
import com.service.JwtService;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void initAdmin() {
        String adminEmail = "admin@univ-brest.fr";
        log.info(">>>> INITIALISATION : Vérification de l'utilisateur admin '{}'", adminEmail);
        try {
            User admin = userRepository.findByEmail(adminEmail).orElse(null);
            if (admin == null) {
                log.info(">>>> INITIALISATION : Admin non trouvé, création...");
                admin = User.builder()
                        .email(adminEmail)
                        .username("admin")
                        .nom("Admin")
                        .prenom("Super")
                        .password(passwordEncoder.encode("password123"))
                        .role("ADMIN")
                        .build();
                userRepository.save(admin);
                log.info(">>>> INITIALISATION : Admin créé avec succès.");
            } else {
                log.info(">>>> INITIALISATION : Admin trouvé (ID={}). Mise à jour du mot de passe...", admin.getId());
                admin.setPassword(passwordEncoder.encode("password123"));
                userRepository.save(admin);
                log.info(">>>> INITIALISATION : Mot de passe admin mis à jour.");
            }
        } catch (Exception e) {
            log.error(">>>> INITIALISATION : Erreur lors de la vérification de l'admin: {}", e.getMessage());
        }
    }

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        // On suppose que votre jwtService utilise getUsername() (Spring Security) qui
        // renvoie notre email configuré
        String token = jwtService.generateToken(user);

        return AuthResponseDto.builder()
                .accessToken(token)
                .expiresIn(3600)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        log.info(">>>> TENTATIVE DE CONNEXION : email='{}'", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            log.error(">>>> UTILISATEUR NON TROUVÉ en base pour l'email: '{}'", request.getEmail());
            // On laisse l'auth manager lancer l'exception pour la sécurité
        } else {
            log.info(">>>> UTILISATEUR TROUVÉ : ID={}, pseudo={}, role={}", user.getId(), user.getUsername(),
                    user.getRole());
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (user == null) { // Devrait être impossible car l'auth manager a réussi ou lancé une exception
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
