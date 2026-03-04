package com.services;

import com.dtos.PosterDto;
import com.dtos.PosterInputDto;
import java.util.List;

/**
 * Interface du service de gestion des posters.
 */
public interface PosterService {
    List<PosterDto> getAllPosters();
    PosterDto getPosterById(Long id);
    PosterDto createPoster(PosterInputDto posterInputDto);
    PosterDto updatePoster(Long id, PosterInputDto posterInputDto);
    void deletePoster(Long id);
}
