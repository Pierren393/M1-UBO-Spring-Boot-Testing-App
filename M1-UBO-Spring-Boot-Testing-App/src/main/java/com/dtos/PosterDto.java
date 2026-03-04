package com.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de sortie représentant un poster.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosterDto {
    private Long id;
    private String title;
    private String imageUrl;
    private Long movieId;
    private String description;
    private Double price;
    private DimensionsDto dimensions;
}
