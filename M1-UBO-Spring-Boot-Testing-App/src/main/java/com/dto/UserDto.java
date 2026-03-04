package com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de sortie représentant un utilisateur (sans mot de passe).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String pseudo;
    private String nom;
    private String prenom;
    private Integer age;
    private String adresse;
    private String role;
}
