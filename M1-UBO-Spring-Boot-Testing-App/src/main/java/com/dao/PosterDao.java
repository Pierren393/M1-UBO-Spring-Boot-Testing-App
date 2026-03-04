package com.dao;

import com.entities.Poster;
import java.util.List;
import java.util.Optional;

/**
 * Interface DAO pour l'accès aux données des posters (MongoDB).
 */
public interface PosterDao {
    Poster save(Poster poster);
    Optional<Poster> findById(String id);
    List<Poster> findAll();
    List<Poster> findByMovieId(Long movieId);
    boolean existsById(String id);
    void deleteById(String id);
    long count();
}
