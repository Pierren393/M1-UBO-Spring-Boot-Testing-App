package com.mappers;

import com.dtos.AuthentificationDto;
import com.entities.Authentification;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper responsable de la conversion entre les entités Authentification et les DTOs AuthentificationDto.
 * Un mapper permet de séparer la couche de persistance de la couche de présentation.
 * Points clés du pattern Mapper :
 * - Conversion bidirectionnelle entre DTO et Entity
 * - Gestion des null-safety
 * - Pas de logique métier, uniquement de la transformation
 */
@Component
public class AuthentificationMapper {

    /**
     * Convertit une entité Authentification en DTO AuthentificationDto
     * Cette méthode est utilisée pour exposer les données aux clients de l'API
     *
     * @param authentification l'entité à convertir
     * @return le DTO correspondant ou null si l'entité est null
     */
    public AuthentificationDto toDto(Authentification authentification) {
        if (authentification == null) {
            return null;
        }

        AuthentificationDto authentificationDto = new AuthentificationDto();
        authentificationDto.setId(authentification.getId());
        authentificationDto.setName(authentification.getName());
        authentificationDto.setRace(authentification.getRace());
        return authentificationDto;
    }

    /**
     * Convertit un DTO AuthentificationDto en entité Authentification
     * Cette méthode est utilisée pour persister les données reçues des clients
     * Note: La date de naissance n'est pas dans le DTO mais est présente dans l'entité
     *
     * @param authentificationDto le DTO à convertir
     * @return l'entité correspondante ou null si le DTO est null
     */
    public Authentification toEntity(AuthentificationDto authentificationDto) {
        if (authentificationDto == null) {
            return null;
        }

        Authentification authentification = new Authentification();
        // On ne set l'ID que s'il existe (cas d'une mise à jour)
        if (authentificationDto.getId() != null) {
            authentification.setId(authentificationDto.getId());
        }
        authentification.setName(authentificationDto.getName());
        authentification.setRace(authentificationDto.getRace());
        return authentification;
    }
} 