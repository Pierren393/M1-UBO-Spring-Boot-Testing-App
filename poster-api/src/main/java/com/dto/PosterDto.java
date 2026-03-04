package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de sortie représentant un poster.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosterDto {
    private String id;
    private String titreFilm;
    private String realisateur;
    private Integer anneeSortie;
    private List<String> genres;
    private String synopsis;
    private List<String> acteursPrincipaux;
    private String imageUrl;
    private Long movieId;
    private String description;
    private Double price;
    private DimensionsDto dimensions;
}
