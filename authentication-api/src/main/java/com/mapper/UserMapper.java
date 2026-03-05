package com.mapper;

import com.dto.RegisterRequestDto;
import com.dto.UpdateUserRequestDto;
import com.dto.UserDto;
import com.entity.User;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Mapper pour convertir entre l'entité User et ses DTOs.
 */
@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null)
            return null;
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .age(user.getAge())
                .adresse(user.getAdresse())
                .role(user.getRole())
                .build();
    }

    public User toEntity(RegisterRequestDto dto) {
        if (dto == null)
            return null;
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nom(dto.getNom() != null ? dto.getNom() : "")
                .prenom(dto.getPrenom() != null ? dto.getPrenom() : "")
                .age(dto.getAge())
                .adresse(dto.getAdresse() != null ? dto.getAdresse() : "")
                .role("USER")
                .build();
    }

    public void updateEntity(UpdateUserRequestDto dto, User user) {
        if (dto == null || user == null)
            return;
        if (dto.getUsername() != null && !dto.getUsername().isBlank())
            user.setUsername(dto.getUsername());
        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            user.setEmail(dto.getEmail());
        if (dto.getNom() != null)
            user.setNom(dto.getNom());
        if (dto.getPrenom() != null)
            user.setPrenom(dto.getPrenom());
        if (dto.getAge() != null)
            user.setAge(dto.getAge());
        if (dto.getAdresse() != null)
            user.setAdresse(dto.getAdresse());
    }
}
