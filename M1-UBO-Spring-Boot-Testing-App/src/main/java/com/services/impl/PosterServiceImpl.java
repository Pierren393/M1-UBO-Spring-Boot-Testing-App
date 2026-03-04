package com.services.impl;

import com.dtos.PosterDto;
import com.dtos.PosterInputDto;
import com.entities.Poster;
import com.mappers.PosterMapper;
import com.repositories.PosterRepository;
import com.services.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des posters.
 */
@Service
@RequiredArgsConstructor
public class PosterServiceImpl implements PosterService {

    private final PosterRepository posterRepository;
    private final PosterMapper posterMapper;

    @Override
    public List<PosterDto> getAllPosters() {
        return posterRepository.findAll().stream().map(posterMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PosterDto getPosterById(Long id) {
        Poster poster = posterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poster non trouvé"));
        return posterMapper.toDto(poster);
    }

    @Override
    public PosterDto createPoster(PosterInputDto posterInputDto) {
        Poster poster = posterMapper.toEntity(posterInputDto);
        poster = posterRepository.save(poster);
        return posterMapper.toDto(poster);
    }

    @Override
    public PosterDto updatePoster(Long id, PosterInputDto posterInputDto) {
        Poster poster = posterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Poster non trouvé"));
        posterMapper.updateEntity(posterInputDto, poster);
        poster = posterRepository.save(poster);
        return posterMapper.toDto(poster);
    }

    @Override
    public void deletePoster(Long id) {
        if (!posterRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Poster non trouvé");
        }
        posterRepository.deleteById(id);
    }
}
