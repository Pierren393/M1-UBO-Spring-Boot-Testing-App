package com.repository;

import com.entity.Poster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosterRepository extends MongoRepository<Poster, String> {
    List<Poster> findByMovieId(Long movieId);
}
