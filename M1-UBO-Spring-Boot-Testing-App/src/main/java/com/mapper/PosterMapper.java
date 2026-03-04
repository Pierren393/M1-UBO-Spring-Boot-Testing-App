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
        dto.setTitle(poster.getTitle());
        dto.setImageUrl(poster.getImageUrl());
        dto.setMovieId(poster.getMovieId());
        dto.setDescription(poster.getDescription());
        dto.setPrice(poster.getPrice());
        dto.setDimensions(new DimensionsDto(poster.getWidth(), poster.getHeight()));
        return dto;
    }

    public Poster toEntity(PosterInputDto dto) {
        if (dto == null) return null;
        Poster poster = new Poster();
        poster.setTitle(dto.getTitle());
        poster.setImageUrl(dto.getImageUrl());
        poster.setMovieId(dto.getMovieId());
        poster.setDescription(dto.getDescription());
        poster.setPrice(dto.getPrice());
        poster.setWidth(dto.getWidth());
        poster.setHeight(dto.getHeight());
        return poster;
    }

    public void updateEntity(PosterInputDto dto, Poster poster) {
        if (dto == null || poster == null) return;
        if (dto.getTitle() != null) poster.setTitle(dto.getTitle());
        if (dto.getImageUrl() != null) poster.setImageUrl(dto.getImageUrl());
        if (dto.getMovieId() != null) poster.setMovieId(dto.getMovieId());
        if (dto.getDescription() != null) poster.setDescription(dto.getDescription());
        if (dto.getPrice() != null) poster.setPrice(dto.getPrice());
        if (dto.getWidth() != null) poster.setWidth(dto.getWidth());
        if (dto.getHeight() != null) poster.setHeight(dto.getHeight());
    }
}
