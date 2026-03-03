package com.controllers;

import com.dtos.UpdateUserRequestDto;
import com.dtos.UserDto;
import com.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur pour la gestion des utilisateurs : consultation, mise à jour, suppression.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * GET /users/{id} - Trouver un utilisateur par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = authenticationService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * PUT /users/{id} - Mettre à jour un utilisateur
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @RequestBody UpdateUserRequestDto updateRequest) {
        UserDto user = authenticationService.updateUser(id, updateRequest);
        return ResponseEntity.ok(user);
    }

    /**
     * DELETE /users/{id} - Supprimer un utilisateur
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        authenticationService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
