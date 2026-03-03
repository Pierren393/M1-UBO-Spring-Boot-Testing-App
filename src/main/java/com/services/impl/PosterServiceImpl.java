package com.services.impl;

import com.dtos.PosterDto;
import com.dtos.PosterInputDto;
import com.entities.Poster;
import com.mappers.PosterMapper;
import com.repositories.PosterRepository;
import com.services.PosterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation du service de gestion des posters de films.
 */
@Service("posterService")
@Transactional
public class PosterServiceImpl implements PosterService {

    private final PosterRepository posterRepository;
    private final PosterMapper posterMapper;

    public PosterServiceImpl(PosterRepository posterRepository, PosterMapper posterMapper) {
        this.posterRepository = posterRepository;
        this.posterMapper = posterMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PosterDto> getAllPosters(String movieId) {
        List<Poster> posters;
        if (movieId != null && !movieId.isBlank()) {
            posters = posterRepository.findByMovieId(movieId);
        } else {
            posters = posterRepository.findAll();
        }
        return posters.stream()
                .map(posterMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PosterDto getPosterById(Long id) {
        Poster poster = posterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Le poster avec l'ID %d n'existe pas", id)));
        return posterMapper.toDto(poster);
    }

    @Override
    public PosterDto createPoster(PosterInputDto posterInputDto) {
        Poster poster = posterMapper.toEntity(posterInputDto);
        Poster savedPoster = posterRepository.save(poster);
        return posterMapper.toDto(savedPoster);
    }

    @Override
    public PosterDto updatePoster(Long id, PosterInputDto posterInputDto) {
        Poster poster = posterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Le poster avec l'ID %d n'existe pas", id)));

        posterMapper.updateEntity(poster, posterInputDto);
        Poster updatedPoster = posterRepository.save(poster);
        return posterMapper.toDto(updatedPoster);
    }

    @Override
    public void deletePoster(Long id) {
        if (!posterRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("Le poster avec l'ID %d n'existe pas", id));
        }
        posterRepository.deleteById(id);
    }
}
