package com.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "posters")
@Data
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String movieId;

    private String titreFilm;

    private String realisateur;

    private Integer anneeSortie;

    @ElementCollection
    @CollectionTable(name = "poster_genres", joinColumns = @JoinColumn(name = "poster_id"))
    @Column(name = "genre")
    private List<String> genres;

    @Column(nullable = false)
    private String imageUrl;

    @Column(length = 1000)
    private String synopsis;

    @ElementCollection
    @CollectionTable(name = "poster_acteurs", joinColumns = @JoinColumn(name = "poster_id"))
    @Column(name = "acteur")
    private List<String> acteursPrincipaux;

    private Integer largeur;

    private Integer hauteur;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateAjout;

    @PrePersist
    protected void onCreate() {
        this.dateAjout = LocalDateTime.now();
    }
}
