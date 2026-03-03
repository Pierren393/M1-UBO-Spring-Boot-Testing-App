package com.controllers;

import com.dtos.*;
import com.services.AuthenticationService;
import com.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour l'authentification : inscription, connexion, déconnexion, profil.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    /**
     * POST /auth/register - Enregistrer un nouvel utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequestDto registerRequest) {
        UserDto user = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * POST /auth/login - Connexion utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        AuthResponseDto response = authenticationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /auth/logout - Déconnexion utilisateur
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        // Dans une implémentation simplifiée, le logout se fait côté client
        // en supprimant le token. Ici on retourne juste 200.
        return ResponseEntity.ok().build();
    }

    /**
     * GET /auth/me - Obtenir l'utilisateur courant
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        if (token == null || !jwtService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto user = authenticationService.getCurrentUser(token);
        return ResponseEntity.ok(user);
    }

    /**
     * Extrait le token du header Authorization "Bearer xxx"
     */
    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
