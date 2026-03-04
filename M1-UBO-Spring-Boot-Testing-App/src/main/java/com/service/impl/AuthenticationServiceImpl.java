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

import java.util.List;
import java.util.stream.Collectors;

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
        if (userDao.existsByPseudo(request.getPseudo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce pseudo est déjà utilisé");
        }
        User user = userMapper.toEntity(request);
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        userDao.save(user);
        String token = jwtService.generateToken(user);
        return AuthResponseDto.builder().token(token).user(userMapper.toDto(user)).build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPseudo(), request.getMotDePasse()));
        User user = userDao.findByPseudo(request.getPseudo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        String token = jwtService.generateToken(user);
        return AuthResponseDto.builder().token(token).user(userMapper.toDto(user)).build();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDao.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByPseudo(String pseudo) {
        User user = userDao.findByPseudo(pseudo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(String pseudo, UpdateUserRequestDto request, String currentUserPseudo) {
        User user = userDao.findByPseudo(pseudo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        if (!pseudo.equals(currentUserPseudo) && !isAdmin(currentUserPseudo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit");
        }
        userMapper.updateEntity(request, user);
        if (request.getMotDePasse() != null && !request.getMotDePasse().isBlank()) {
            user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        }
        userDao.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void deleteUser(String pseudo, String currentUserPseudo) {
        User user = userDao.findByPseudo(pseudo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        if (!pseudo.equals(currentUserPseudo) && !isAdmin(currentUserPseudo)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès interdit");
        }
        userDao.delete(user);
    }

    private boolean isAdmin(String pseudo) {
        return userDao.findByPseudo(pseudo).map(u -> "ADMIN".equals(u.getRole())).orElse(false);
    }
}
