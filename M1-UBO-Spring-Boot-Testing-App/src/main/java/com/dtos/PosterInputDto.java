package com.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO d'entrée pour la création/modification d'un poster.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosterInputDto {

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    private String imageUrl;

    @NotNull(message = "L'identifiant du film est obligatoire")
    private Long movieId;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    private Double price;

    private Double width;
    private Double height;
}
