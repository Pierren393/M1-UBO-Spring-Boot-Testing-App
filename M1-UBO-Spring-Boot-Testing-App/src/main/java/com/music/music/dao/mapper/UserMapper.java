package com.music.music.dao.mapper;

import com.music.music.dao.dto.RegisterRequestDto;
import com.music.music.dao.dto.UpdateUserRequestDto;
import com.music.music.dao.dto.UserDto;
import com.music.music.dao.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir entre l'entité User et ses DTOs.
 */
@Component
public class UserMapper {

    /**
     * Convertit une entité User en UserDto.
     * @param user l'entité à convertir
     * @return le DTO correspondant
     */
    public UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .pseudo(user.getPseudo())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .age(user.getAge())
                .adresse(user.getAdresse())
                .role(user.getRole())
                .build();
    }

    /**
     * Convertit un RegisterRequestDto en entité User.
     * @param dto le DTO de création
     * @return l'entité User correspondante
     */
    public User toEntity(RegisterRequestDto dto) {
        if (dto == null) return null;
        return User.builder()
                .pseudo(dto.getPseudo())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .age(dto.getAge())
                .adresse(dto.getAdresse())
                .motDePasse(dto.getMotDePasse())
                .role("USER")
                .build();
    }

    /**
     * Met à jour une entité User à partir d'un UpdateUserRequestDto.
     * Seuls les champs non null sont mis à jour.
     * @param dto le DTO de mise à jour
     * @param user l'entité à mettre à jour
     */
    public void updateEntity(UpdateUserRequestDto dto, User user) {
        if (dto == null || user == null) return;
        if (dto.getNom() != null) user.setNom(dto.getNom());
        if (dto.getPrenom() != null) user.setPrenom(dto.getPrenom());
        if (dto.getAge() != null) user.setAge(dto.getAge());
        if (dto.getAdresse() != null) user.setAdresse(dto.getAdresse());
    }
}
