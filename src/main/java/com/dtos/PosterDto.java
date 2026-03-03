package com.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PosterDto {

    private Long id;
    private String movieId;
    private String titreFilm;
    private String realisateur;
    private Integer anneeSortie;
    private List<String> genres;
    private String imageUrl;
    private String synopsis;
    private List<String> acteursPrincipaux;
    private DimensionsDto dimensions;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime dateAjout;
}
