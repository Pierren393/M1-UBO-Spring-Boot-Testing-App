package com.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant un poster de film.
 */
@Entity
@Table(name = "poster")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "movie_id")
    private Long movieId;

    private String description;
    private Double price;
    private Double width;
    private Double height;
}
