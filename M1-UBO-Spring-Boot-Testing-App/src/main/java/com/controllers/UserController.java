package com.controllers;

import com.dtos.*;
import com.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des utilisateurs et l'authentification.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs et authentification")
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping
    @Operation(summary = "Créer un utilisateur", description = "Inscription d'un nouvel utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Le pseudo existe déjà")
    })
    public ResponseEntity<AuthResponseDto> createUser(@Valid @RequestBody RegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Authentification d'un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Connexion réussie"),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les utilisateurs", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des utilisateurs"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(authenticationService.getAllUsers());
    }

    @GetMapping("/{pseudo}")
    @Operation(summary = "Récupérer un utilisateur par pseudo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UserDto> getUserByPseudo(@PathVariable String pseudo) {
        return ResponseEntity.ok(authenticationService.getUserByPseudo(pseudo));
    }

    @PutMapping("/{pseudo}")
    @Operation(summary = "Modifier un utilisateur", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur modifié"),
            @ApiResponse(responseCode = "403", description = "Accès interdit"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UserDto> updateUser(@PathVariable String pseudo,
                                               @Valid @RequestBody UpdateUserRequestDto request,
                                               Authentication authentication) {
        return ResponseEntity.ok(authenticationService.updateUser(pseudo, request, authentication.getName()));
    }

    @DeleteMapping("/{pseudo}")
    @Operation(summary = "Supprimer un utilisateur", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur supprimé"),
            @ApiResponse(responseCode = "403", description = "Accès interdit"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable String pseudo, Authentication authentication) {
        authenticationService.deleteUser(pseudo, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
