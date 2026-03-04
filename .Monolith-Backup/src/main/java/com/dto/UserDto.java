package com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO de sortie représentant un utilisateur (sans mot de passe).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    
    // Autres champs contextuels
    private String nom;
    private String prenom;
    private Integer age;
    private String adresse;
    private String role;
}
