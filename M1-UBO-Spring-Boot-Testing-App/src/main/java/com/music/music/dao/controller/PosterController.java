package com.music.music.dao.controller;

import com.music.music.dao.dto.PosterDto;
import com.music.music.dao.dto.PosterInputDto;
import com.music.music.dao.service.PosterService;
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

    /**
     * Récupère tous les posters.
     * @return la liste de tous les posters
     */
    @GetMapping
    @Operation(summary = "Lister tous les posters", description = "Récupère la liste de tous les posters")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des posters récupérée avec succès")
    })
    public ResponseEntity<List<PosterDto>> getAllPosters() {
        return ResponseEntity.ok(posterService.getAllPosters());
    }

    /**
     * Récupère un poster par son identifiant.
     * @param id l'identifiant du poster
     * @return le poster correspondant
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un poster", description = "Récupère un poster par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Poster trouvé"),
            @ApiResponse(responseCode = "404", description = "Poster non trouvé")
    })
    public ResponseEntity<PosterDto> getPosterById(@PathVariable Long id) {
        return ResponseEntity.ok(posterService.getPosterById(id));
    }

    /**
     * Crée un nouveau poster.
     * @param posterInputDto les données du poster à créer
     * @return le poster créé
     */
    @PostMapping
    @Operation(summary = "Créer un poster", description = "Crée un nouveau poster",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Poster créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    public ResponseEntity<PosterDto> createPoster(@Valid @RequestBody PosterInputDto posterInputDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(posterService.createPoster(posterInputDto));
    }

    /**
     * Met à jour un poster existant.
     * @param id l'identifiant du poster à modifier
     * @param posterInputDto les nouvelles données du poster
     * @return le poster mis à jour
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un poster", description = "Met à jour un poster existant",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Poster modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "404", description = "Poster non trouvé")
    })
    public ResponseEntity<PosterDto> updatePoster(@PathVariable Long id, @Valid @RequestBody PosterInputDto posterInputDto) {
        return ResponseEntity.ok(posterService.updatePoster(id, posterInputDto));
    }

    /**
     * Supprime un poster.
     * @param id l'identifiant du poster à supprimer
     * @return 204 No Content si supprimé
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un poster", description = "Supprime un poster existant",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Poster supprimé avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "404", description = "Poster non trouvé")
    })
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        posterService.deletePoster(id);
        return ResponseEntity.noContent().build();
    }
}
