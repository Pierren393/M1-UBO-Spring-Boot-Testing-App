package com.controllers;

import com.dtos.PosterDto;
import com.dtos.PosterInputDto;
import com.services.PosterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des posters.
 */
@RestController
@RequestMapping("/poster")
@RequiredArgsConstructor
@Tag(name = "Posters", description = "API de gestion des posters de films")
public class PosterController {

    private final PosterService posterService;

    @GetMapping
    @Operation(summary = "Lister tous les posters")
    @ApiResponse(responseCode = "200", description = "Liste des posters")
    public ResponseEntity<List<PosterDto>> getAllPosters() {
        return ResponseEntity.ok(posterService.getAllPosters());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un poster")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Poster trouvé"),
            @ApiResponse(responseCode = "404", description = "Poster non trouvé")
    })
    public ResponseEntity<PosterDto> getPosterById(@PathVariable Long id) {
        return ResponseEntity.ok(posterService.getPosterById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un poster", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Poster créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    public ResponseEntity<PosterDto> createPoster(@Valid @RequestBody PosterInputDto posterInputDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(posterService.createPoster(posterInputDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un poster", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Poster modifié"),
            @ApiResponse(responseCode = "404", description = "Poster non trouvé")
    })
    public ResponseEntity<PosterDto> updatePoster(@PathVariable Long id, @Valid @RequestBody PosterInputDto posterInputDto) {
        return ResponseEntity.ok(posterService.updatePoster(id, posterInputDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un poster", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Poster supprimé"),
            @ApiResponse(responseCode = "404", description = "Poster non trouvé")
    })
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        posterService.deletePoster(id);
        return ResponseEntity.noContent().build();
    }
}
