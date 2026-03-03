package com.repositories;

import com.entities.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {

    List<Poster> findByMovieId(String movieId);
}
