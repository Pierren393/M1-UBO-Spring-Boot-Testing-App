package com.services;

import com.dtos.PosterDto;
import com.dtos.PosterInputDto;

import java.util.List;

/**
 * Interface définissant les opérations métier pour la gestion des posters de
 * films.
 */
public interface PosterService {

    /**
     * Récupère tous les posters, avec filtre optionnel par movieId
     * 
     * @param movieId l'ID du film pour filtrer (peut être null)
     * @return la liste des posters
     */
    List<PosterDto> getAllPosters(String movieId);

    /**
     * Récupère un poster par son identifiant
     * 
     * @param id l'identifiant du poster
     * @return le poster trouvé
     */
    PosterDto getPosterById(Long id);

    /**
     * Crée un nouveau poster
     * 
     * @param posterInputDto les données du poster à créer
     * @return le poster créé avec son ID généré
     */
    PosterDto createPoster(PosterInputDto posterInputDto);

    /**
     * Met à jour un poster existant
     * 
     * @param id             l'identifiant du poster à mettre à jour
     * @param posterInputDto les nouvelles données
     * @return le poster mis à jour
     */
    PosterDto updatePoster(Long id, PosterInputDto posterInputDto);

    /**
     * Supprime un poster
     * 
     * @param id l'identifiant du poster à supprimer
     */
    void deletePoster(Long id);
}
