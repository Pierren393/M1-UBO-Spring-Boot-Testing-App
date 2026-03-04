package com.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la mise à jour d'un utilisateur.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {

    @Email(message = "L'email n'est pas valide")
    private String email;

    private String username;

    private String password;

    // Facultatifs contextuels
    private String nom;
    private String prenom;
    private Integer age;
    private String adresse;
}
