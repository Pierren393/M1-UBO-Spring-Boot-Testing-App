package com.music.music.dao.controller;

import com.music.music.dao.dto.*;
import com.music.music.dao.service.AuthenticationService;
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
 * Contrôleur REST pour la gestion des utilisateurs.
 * Regroupe les endpoints d'authentification et de CRUD utilisateur.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs et authentification")
public class UserController {

    private final AuthenticationService authenticationService;

    /**
     * Crée un nouvel utilisateur.
     * @param request les données d'inscription
     * @return la réponse d'authentification avec token JWT
     */
    @PostMapping
    @Operation(summary = "Créer un utilisateur", description = "Inscription d'un nouvel utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Le pseudo existe déjà")
    })
    public ResponseEntity<AuthResponseDto> createUser(@Valid @RequestBody RegisterRequestDto request) {
        AuthResponseDto response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Connecte un utilisateur.
     * @param request les données de connexion
     * @return la réponse d'authentification avec token JWT
     */
    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Authentification d'un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Connexion réussie"),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupère la liste de tous les utilisateurs (admin uniquement).
     * @return la liste des utilisateurs
     */
    @GetMapping
    @Operation(summary = "Lister tous les utilisateurs", description = "Accessible uniquement aux administrateurs",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des utilisateurs"),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "403", description = "Accès interdit (admin requis)")
    })
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(authenticationService.getAllUsers());
    }

    /**
     * Récupère un utilisateur par son pseudo.
     * @param pseudo le pseudo de l'utilisateur
     * @return le DTO de l'utilisateur
     */
    @GetMapping("/{pseudo}")
    @Operation(summary = "Récupérer un utilisateur", description = "Récupère un utilisateur par son pseudo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UserDto> getUserByPseudo(@PathVariable String pseudo) {
        return ResponseEntity.ok(authenticationService.getUserByPseudo(pseudo));
    }

    /**
     * Met à jour un utilisateur.
     * @param pseudo le pseudo de l'utilisateur à modifier
     * @param request les données de mise à jour
     * @param authentication l'authentification courante
     * @return le DTO de l'utilisateur mis à jour
     */
    @PutMapping("/{pseudo}")
    @Operation(summary = "Modifier un utilisateur", description = "Modification d'un utilisateur (soi-même ou admin)",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "403", description = "Accès interdit"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UserDto> updateUser(
            @PathVariable String pseudo,
            @Valid @RequestBody UpdateUserRequestDto request,
            Authentication authentication) {
        String currentPseudo = authentication.getName();
        return ResponseEntity.ok(authenticationService.updateUser(pseudo, request, currentPseudo));
    }

    /**
     * Supprime un utilisateur.
     * @param pseudo le pseudo de l'utilisateur à supprimer
     * @param authentication l'authentification courante
     * @return 200 si supprimé
     */
    @DeleteMapping("/{pseudo}")
    @Operation(summary = "Supprimer un utilisateur", description = "Suppression d'un utilisateur (admin ou soi-même)",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur supprimé"),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "403", description = "Accès interdit"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable String pseudo, Authentication authentication) {
        String currentPseudo = authentication.getName();
        authenticationService.deleteUser(pseudo, currentPseudo);
        return ResponseEntity.ok().build();
    }
}
