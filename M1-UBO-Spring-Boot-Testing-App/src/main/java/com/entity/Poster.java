package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant un poster de film (stocké dans MongoDB).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poster {

    private String id;
    private String title;
    private String imageUrl;
    private Long movieId;
    private String description;
    private Double price;
    private Double width;
    private Double height;
}
