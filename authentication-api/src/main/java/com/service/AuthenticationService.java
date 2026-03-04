package com.service;

import com.dto.*;

/**
 * Interface du service d'authentification et de gestion des utilisateurs.
 */
public interface AuthenticationService {
    AuthResponseDto register(RegisterRequestDto request);
    AuthResponseDto login(LoginRequestDto request);
    UserDto getCurrentUser(String email);
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UpdateUserRequestDto request, String currentUserEmail);
    void deleteUser(Long id, String currentUserEmail);
}
