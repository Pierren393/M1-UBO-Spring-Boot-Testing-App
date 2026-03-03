package com.music.music.dao.service;

import com.music.music.dao.dto.PosterDto;
import com.music.music.dao.dto.PosterInputDto;
import com.music.music.dao.dto.DimensionsDto;
import com.music.music.dao.entity.Poster;
import com.music.music.dao.mapper.PosterMapper;
import com.music.music.dao.repository.PosterRepository;
import com.music.music.dao.service.impl.PosterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour le service de gestion des posters.
 */
@ExtendWith(MockitoExtension.class)
class PosterServiceImplTest {

    @Mock
    private PosterRepository posterRepository;

    @Mock
    private PosterMapper posterMapper;

    @InjectMocks
    private PosterServiceImpl posterService;

    private Poster testPoster;
    private PosterDto testPosterDto;
    private PosterInputDto testPosterInputDto;

    @BeforeEach
    void setUp() {
        testPoster = new Poster();
        testPoster.setId(1L);
        testPoster.setTitle("Inception");
        testPoster.setImageUrl("https://example.com/inception.jpg");
        testPoster.setMovieId(27205L);
        testPoster.setDescription("Poster Inception");
        testPoster.setPrice(19.99);
        testPoster.setWidth(60.0);
        testPoster.setHeight(90.0);

        DimensionsDto dimensions = new DimensionsDto();
        dimensions.setWidth(60.0);
        dimensions.setHeight(90.0);

        testPosterDto = new PosterDto();
        testPosterDto.setId(1L);
        testPosterDto.setTitle("Inception");
        testPosterDto.setImageUrl("https://example.com/inception.jpg");
        testPosterDto.setMovieId(27205L);
        testPosterDto.setDescription("Poster Inception");
        testPosterDto.setPrice(19.99);
        testPosterDto.setDimensions(dimensions);

        testPosterInputDto = new PosterInputDto();
        testPosterInputDto.setTitle("Inception");
        testPosterInputDto.setImageUrl("https://example.com/inception.jpg");
        testPosterInputDto.setMovieId(27205L);
        testPosterInputDto.setDescription("Poster Inception");
        testPosterInputDto.setPrice(19.99);
        testPosterInputDto.setWidth(60.0);
        testPosterInputDto.setHeight(90.0);
    }

    @Test
    void getAllPosters_shouldReturnList() {
        when(posterRepository.findAll()).thenReturn(List.of(testPoster));
        when(posterMapper.toDto(testPoster)).thenReturn(testPosterDto);

        List<PosterDto> result = posterService.getAllPosters();

        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    void getPosterById_shouldReturnPoster_whenExists() {
        when(posterRepository.findById(1L)).thenReturn(Optional.of(testPoster));
        when(posterMapper.toDto(testPoster)).thenReturn(testPosterDto);

        PosterDto result = posterService.getPosterById(1L);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }

    @Test
    void getPosterById_shouldThrowNotFound_whenNotExists() {
        when(posterRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> posterService.getPosterById(99L));
    }

    @Test
    void createPoster_shouldSaveAndReturn() {
        when(posterMapper.toEntity(testPosterInputDto)).thenReturn(testPoster);
        when(posterRepository.save(testPoster)).thenReturn(testPoster);
        when(posterMapper.toDto(testPoster)).thenReturn(testPosterDto);

        PosterDto result = posterService.createPoster(testPosterInputDto);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
        verify(posterRepository).save(testPoster);
    }

    @Test
    void deletePoster_shouldDelete_whenExists() {
        when(posterRepository.existsById(1L)).thenReturn(true);

        posterService.deletePoster(1L);

        verify(posterRepository).deleteById(1L);
    }

    @Test
    void deletePoster_shouldThrowNotFound_whenNotExists() {
        when(posterRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> posterService.deletePoster(99L));
    }
}
