package com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO d'entrée pour la création/modification d'un poster.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosterInputDto {

    @NotBlank(message = "Le titre est obligatoire")
    private String titreFilm;

    private String realisateur;
    private Integer anneeSortie;
    private List<String> genres;
    private String synopsis;
    private List<String> acteursPrincipaux;

    private String imageUrl;

    @NotNull(message = "L'identifiant du film est obligatoire")
    private Long movieId;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    private Double price;

    private DimensionsDto dimensions;
}
