package com.music.music.dao.service;

import com.music.music.dao.dto.*;

import java.util.List;

/**
 * Interface du service d'authentification et de gestion des utilisateurs.
 */
public interface AuthenticationService {

    /**
     * Inscription d'un nouvel utilisateur.
     * @param request les données d'inscription
     * @return la réponse d'authentification avec token JWT
     */
    AuthResponseDto register(RegisterRequestDto request);

    /**
     * Connexion d'un utilisateur.
     * @param request les données de connexion
     * @return la réponse d'authentification avec token JWT
     */
    AuthResponseDto login(LoginRequestDto request);

    /**
     * Récupère tous les utilisateurs (admin uniquement).
     * @return la liste de tous les utilisateurs
     */
    List<UserDto> getAllUsers();

    /**
     * Récupère un utilisateur par son pseudo.
     * @param pseudo le pseudo de l'utilisateur
     * @return le DTO de l'utilisateur
     */
    UserDto getUserByPseudo(String pseudo);

    /**
     * Met à jour un utilisateur.
     * @param pseudo le pseudo de l'utilisateur à modifier
     * @param request les données de mise à jour
     * @param currentUserPseudo le pseudo de l'utilisateur connecté
     * @return le DTO de l'utilisateur mis à jour
     */
    UserDto updateUser(String pseudo, UpdateUserRequestDto request, String currentUserPseudo);

    /**
     * Supprime un utilisateur (admin ou soi-même).
     * @param pseudo le pseudo de l'utilisateur à supprimer
     * @param currentUserPseudo le pseudo de l'utilisateur connecté
     */
    void deleteUser(String pseudo, String currentUserPseudo);
}
