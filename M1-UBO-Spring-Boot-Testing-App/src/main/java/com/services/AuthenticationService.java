package com.services;

import com.dtos.*;

/**
 * Interface définissant les opérations d'authentification et de gestion des utilisateurs.
 */
public interface AuthenticationService {

    /**
     * Enregistre un nouvel utilisateur
     * @param registerRequest les données d'inscription
     * @return l'utilisateur créé
     */
    UserDto register(RegisterRequestDto registerRequest);

    /**
     * Authentifie un utilisateur et génère les tokens
     * @param loginRequest les identifiants de connexion
     * @return les tokens d'authentification
     */
    AuthResponseDto login(LoginRequestDto loginRequest);

    /**
     * Récupère l'utilisateur courant à partir du token
     * @param token le token JWT
     * @return l'utilisateur authentifié
     */
    UserDto getCurrentUser(String token);

    /**
     * Récupère un utilisateur par son ID
     * @param id l'identifiant de l'utilisateur
     * @return l'utilisateur trouvé
     */
    UserDto getUserById(Long id);

    /**
     * Met à jour un utilisateur
     * @param id l'identifiant de l'utilisateur
     * @param updateRequest les données de mise à jour
     * @return l'utilisateur mis à jour
     */
    UserDto updateUser(Long id, UpdateUserRequestDto updateRequest);

    /**
     * Supprime un utilisateur
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    void deleteUser(Long id);
}
