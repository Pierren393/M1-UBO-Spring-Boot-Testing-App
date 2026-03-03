package com.mappers;

import com.dtos.UserDto;
import com.entities.User;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de la conversion entre l'entité User et le DTO UserDto.
 */
@Component
public class UserMapper {

    /**
     * Convertit une entité User en DTO UserDto (sans le password)
     */
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
}
