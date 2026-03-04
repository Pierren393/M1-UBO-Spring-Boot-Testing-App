package com.dtos;

import jakarta.validation.constraints.Min;
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
    private String nom;
    private String prenom;
    @Min(value = 0, message = "L'âge doit être positif")
    private Integer age;
    private String adresse;
    private String motDePasse;
}
