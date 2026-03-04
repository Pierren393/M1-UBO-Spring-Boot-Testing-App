package com.repositories;

import com.entities.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'accès aux données des posters.
 */
@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {
    List<Poster> findByMovieId(Long movieId);
}
