package com.mapper;

import com.dto.DimensionsDto;
import com.dto.PosterDto;
import com.dto.PosterInputDto;
import com.entity.Poster;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir entre l'entité Poster et ses DTOs.
 */
@Component
public class PosterMapper {

    public PosterDto toDto(Poster poster) {
        if (poster == null) return null;
        PosterDto dto = new PosterDto();
        dto.setId(poster.getId());
        dto.setTitreFilm(poster.getTitle());
        dto.setRealisateur(poster.getRealisateur());
        dto.setAnneeSortie(poster.getAnneeSortie());
        dto.setGenres(poster.getGenres());
        dto.setSynopsis(poster.getSynopsis());
        dto.setActeursPrincipaux(poster.getActeursPrincipaux());
        dto.setImageUrl(poster.getImageUrl());
        dto.setMovieId(poster.getMovieId());
        dto.setDescription(poster.getDescription());
        dto.setPrice(poster.getPrice());
        if (poster.getWidth() != null || poster.getHeight() != null) {
            dto.setDimensions(new DimensionsDto(poster.getWidth(), poster.getHeight()));
        }
        return dto;
    }

    public Poster toEntity(PosterInputDto dto) {
        if (dto == null) return null;
        Poster poster = new Poster();
        poster.setTitle(dto.getTitreFilm());
        poster.setRealisateur(dto.getRealisateur());
        poster.setAnneeSortie(dto.getAnneeSortie());
        poster.setGenres(dto.getGenres());
        poster.setSynopsis(dto.getSynopsis());
        poster.setActeursPrincipaux(dto.getActeursPrincipaux());
        poster.setImageUrl(dto.getImageUrl());
        poster.setMovieId(dto.getMovieId());
        poster.setDescription(dto.getDescription());
        poster.setPrice(dto.getPrice());
        if (dto.getDimensions() != null) {
            poster.setWidth(dto.getDimensions().getWidth());
            poster.setHeight(dto.getDimensions().getHeight());
        }
        return poster;
    }

    public void updateEntity(PosterInputDto dto, Poster poster) {
        if (dto == null || poster == null) return;
        if (dto.getTitreFilm() != null) poster.setTitle(dto.getTitreFilm());
        if (dto.getRealisateur() != null) poster.setRealisateur(dto.getRealisateur());
        if (dto.getAnneeSortie() != null) poster.setAnneeSortie(dto.getAnneeSortie());
        if (dto.getGenres() != null) poster.setGenres(dto.getGenres());
        if (dto.getSynopsis() != null) poster.setSynopsis(dto.getSynopsis());
        if (dto.getActeursPrincipaux() != null) poster.setActeursPrincipaux(dto.getActeursPrincipaux());
        if (dto.getImageUrl() != null) poster.setImageUrl(dto.getImageUrl());
        if (dto.getMovieId() != null) poster.setMovieId(dto.getMovieId());
        if (dto.getDescription() != null) poster.setDescription(dto.getDescription());
        if (dto.getPrice() != null) poster.setPrice(dto.getPrice());
        if (dto.getDimensions() != null) {
            if (dto.getDimensions().getWidth() != null) poster.setWidth(dto.getDimensions().getWidth());
            if (dto.getDimensions().getHeight() != null) poster.setHeight(dto.getDimensions().getHeight());
        }
    }
}
