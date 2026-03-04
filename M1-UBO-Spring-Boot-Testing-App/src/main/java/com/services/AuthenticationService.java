package com.services;

import com.dtos.*;
import java.util.List;

/**
 * Interface du service d'authentification et de gestion des utilisateurs.
 */
public interface AuthenticationService {
    AuthResponseDto register(RegisterRequestDto request);
    AuthResponseDto login(LoginRequestDto request);
    List<UserDto> getAllUsers();
    UserDto getUserByPseudo(String pseudo);
    UserDto updateUser(String pseudo, UpdateUserRequestDto request, String currentUserPseudo);
    void deleteUser(String pseudo, String currentUserPseudo);
}
