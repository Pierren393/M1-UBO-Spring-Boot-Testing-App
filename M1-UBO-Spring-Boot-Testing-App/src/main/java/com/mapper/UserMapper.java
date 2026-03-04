package com.mapper;

import com.dto.RegisterRequestDto;
import com.dto.UpdateUserRequestDto;
import com.dto.UserDto;
import com.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir entre l'entité User et ses DTOs.
 */
@Component
public class UserMapper {

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

    public void updateEntity(UpdateUserRequestDto dto, User user) {
        if (dto == null || user == null) return;
        if (dto.getNom() != null) user.setNom(dto.getNom());
        if (dto.getPrenom() != null) user.setPrenom(dto.getPrenom());
        if (dto.getAge() != null) user.setAge(dto.getAge());
        if (dto.getAdresse() != null) user.setAdresse(dto.getAdresse());
    }
}
