package com.music.music.dao.service.impl;

import com.music.music.dao.dto.*;
import com.music.music.dao.entity.User;
import com.music.music.dao.mapper.UserMapper;
import com.music.music.dao.repository.UserRepository;
import com.music.music.dao.service.AuthenticationService;
import com.music.music.dao.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service d'authentification et de gestion des utilisateurs.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByPseudo(request.getPseudo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce pseudo est déjà utilisé");
        }

        User user = userMapper.toEntity(request);
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .token(token)
                .user(userMapper.toDto(user))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPseudo(), request.getMotDePasse())
        );

        User user = userRepository.findByPseudo(request.getPseudo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        String token = jwtService.generateToken(user);
        return AuthResponseDto.builder()
                .token(token)
                .user(userMapper.toDto(user))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto getUserByPseudo(String pseudo) {
        User user = userRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return userMapper.toDto(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto updateUser(String pseudo, UpdateUserRequestDto request, String currentUserPseudo) {
        User user = userRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        // Seul l'utilisateur lui-même ou un admin peut modifier
        if (!pseudo.equals(currentUserPseudo) && !isAdmin(currentUserPseudo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit");
        }

        userMapper.updateEntity(request, user);

        if (request.getMotDePasse() != null && !request.getMotDePasse().isBlank()) {
            user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(String pseudo, String currentUserPseudo) {
        User user = userRepository.findByPseudo(pseudo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        // Seul un admin ou l'utilisateur lui-même peut supprimer
        if (!pseudo.equals(currentUserPseudo) && !isAdmin(currentUserPseudo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit");
        }

        userRepository.delete(user);
    }

    /**
     * Vérifie si l'utilisateur courant est un administrateur.
     * @param pseudo le pseudo de l'utilisateur à vérifier
     * @return true si l'utilisateur est admin
     */
    private boolean isAdmin(String pseudo) {
        return userRepository.findByPseudo(pseudo)
                .map(u -> "ADMIN".equals(u.getRole()))
                .orElse(false);
    }
}
