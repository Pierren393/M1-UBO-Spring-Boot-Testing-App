package com.controllers;

import com.dtos.PosterDto;
import com.dtos.PosterInputDto;
import com.services.PosterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des posters de films.
 * Fournit les endpoints CRUD conformes au contrat OpenAPI.
 */
@RestController
@RequestMapping("/posters")
public class PosterController {

    private final PosterService posterService;

    public PosterController(PosterService posterService) {
        this.posterService = posterService;
    }

    /**
     * GET /posters - Liste tous les posters, avec filtre optionnel par movieId
     */
    @GetMapping
    public ResponseEntity<List<PosterDto>> getAllPosters(
            @RequestParam(required = false) String movieId) {
        List<PosterDto> posters = posterService.getAllPosters(movieId);
        return ResponseEntity.ok(posters);
    }

    /**
     * POST /posters - Ajoute un nouveau poster
     */
    @PostMapping
    public ResponseEntity<PosterDto> createPoster(@RequestBody PosterInputDto posterInputDto) {
        PosterDto createdPoster = posterService.createPoster(posterInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPoster);
    }

    /**
     * GET /posters/{id} - Trouver un poster par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PosterDto> getPosterById(@PathVariable Long id) {
        PosterDto poster = posterService.getPosterById(id);
        return ResponseEntity.ok(poster);
    }

    /**
     * PUT /posters/{id} - Met à jour un poster
     */
    @PutMapping("/{id}")
    public ResponseEntity<PosterDto> updatePoster(@PathVariable Long id,
            @RequestBody PosterInputDto posterInputDto) {
        PosterDto updatedPoster = posterService.updatePoster(id, posterInputDto);
        return ResponseEntity.ok(updatedPoster);
    }

    /**
     * DELETE /posters/{id} - Supprime un poster
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        posterService.deletePoster(id);
        return ResponseEntity.noContent().build();
    }
}
