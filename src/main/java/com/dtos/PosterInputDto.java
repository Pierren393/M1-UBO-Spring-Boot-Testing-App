package com.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PosterInputDto {

    private String movieId;
    private String titreFilm;
    private String realisateur;
    private Integer anneeSortie;
    private List<String> genres;
    private String imageUrl;
    private String synopsis;
    private List<String> acteursPrincipaux;
    private DimensionsDto dimensions;
}
