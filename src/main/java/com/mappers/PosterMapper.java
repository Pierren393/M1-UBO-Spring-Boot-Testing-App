package com.mappers;

import com.dtos.DimensionsDto;
import com.dtos.PosterDto;
import com.dtos.PosterInputDto;
import com.entities.Poster;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de la conversion entre l'entité Poster et les DTOs.
 */
@Component
public class PosterMapper {

    /**
     * Convertit une entité Poster en DTO PosterDto
     */
    public PosterDto toDto(Poster poster) {
        if (poster == null) {
            return null;
        }

        PosterDto dto = new PosterDto();
        dto.setId(poster.getId());
        dto.setMovieId(poster.getMovieId());
        dto.setTitreFilm(poster.getTitreFilm());
        dto.setRealisateur(poster.getRealisateur());
        dto.setAnneeSortie(poster.getAnneeSortie());
        dto.setGenres(poster.getGenres());
        dto.setImageUrl(poster.getImageUrl());
        dto.setSynopsis(poster.getSynopsis());
        dto.setActeursPrincipaux(poster.getActeursPrincipaux());
        dto.setDateAjout(poster.getDateAjout());

        // Dimensions
        if (poster.getLargeur() != null || poster.getHauteur() != null) {
            DimensionsDto dimensions = new DimensionsDto();
            dimensions.setLargeur(poster.getLargeur());
            dimensions.setHauteur(poster.getHauteur());
            dto.setDimensions(dimensions);
        }

        return dto;
    }

    /**
     * Convertit un PosterInputDto en entité Poster (création)
     */
    public Poster toEntity(PosterInputDto inputDto) {
        if (inputDto == null) {
            return null;
        }

        Poster poster = new Poster();
        applyInputToEntity(poster, inputDto);
        return poster;
    }

    /**
     * Met à jour une entité Poster existante avec les données du PosterInputDto
     */
    public void updateEntity(Poster poster, PosterInputDto inputDto) {
        if (poster == null || inputDto == null) {
            return;
        }
        applyInputToEntity(poster, inputDto);
    }

    /**
     * Applique les champs du DTO d'entrée sur l'entité
     */
    private void applyInputToEntity(Poster poster, PosterInputDto inputDto) {
        poster.setMovieId(inputDto.getMovieId());
        poster.setTitreFilm(inputDto.getTitreFilm());
        poster.setRealisateur(inputDto.getRealisateur());
        poster.setAnneeSortie(inputDto.getAnneeSortie());
        poster.setGenres(inputDto.getGenres());
        poster.setImageUrl(inputDto.getImageUrl());
        poster.setSynopsis(inputDto.getSynopsis());
        poster.setActeursPrincipaux(inputDto.getActeursPrincipaux());

        if (inputDto.getDimensions() != null) {
            poster.setLargeur(inputDto.getDimensions().getLargeur());
            poster.setHauteur(inputDto.getDimensions().getHauteur());
        } else {
            poster.setLargeur(null);
            poster.setHauteur(null);
        }
    }
}
