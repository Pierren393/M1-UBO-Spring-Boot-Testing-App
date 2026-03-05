package com.controller;

import com.dto.PosterDto;
import com.dto.PosterInputDto;
import com.entity.Poster;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.PosterMapper;
import com.repository.PosterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/poster")
@RequiredArgsConstructor
public class PosterController {

    private final PosterRepository posterRepository;
    private final PosterMapper posterMapper;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<PosterDto> getAll() {
        return posterRepository.findAll().stream()
                .map(posterMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosterDto> getById(@PathVariable String id) {
        return posterRepository.findById(id)
                .map(posterMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestPart("data") String jsonData,
            @RequestPart("image") MultipartFile image) throws IOException {

        PosterInputDto input = objectMapper.readValue(jsonData, PosterInputDto.class);

        if (input.getTitreFilm() == null || input.getTitreFilm().isBlank()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Le titre est obligatoire"));
        }

        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Le fichier 'image' est obligatoire"));
        }

        Poster poster = posterMapper.toEntity(input);

        // Note: Pour l'instant, on simplifie en stockant l'URL ou un simulacre si
        // GridFS n'est pas encore configuré avec Spring Data.
        // Dans une version plus avancée, on utiliserait GridFsTemplate.
        // Pour respecter la transition rapide demandée, on va juste sauvegarder
        // l'entité.
        // On pourrait simuler l'imageUrl si besoin.

        poster = posterRepository.save(poster);
        return ResponseEntity.status(HttpStatus.CREATED).body(posterMapper.toDto(poster));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody PosterInputDto input) {
        return posterRepository.findById(id)
                .map(existing -> {
                    posterMapper.updateEntity(input, existing);
                    Poster saved = posterRepository.save(existing);
                    return ResponseEntity.ok(posterMapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!posterRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        posterRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private record ErrorResponse(String message) {
    }
}
