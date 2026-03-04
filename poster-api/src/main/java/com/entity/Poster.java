package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entité représentant un poster de film (stocké dans MongoDB).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poster {

    private String id;
    
    // Champs OpenAPI
    private String title;          // Mapper verra titreFilm
    private String realisateur;
    private Integer anneeSortie;
    private List<String> genres;
    private String synopsis;
    private List<String> acteursPrincipaux;
    private Double price;
    private Double width;
    private Double height;

    // Métadonnées techniques
    private String imageUrl; // ou ID GridFS
    private Long movieId;
    private String description;
}
