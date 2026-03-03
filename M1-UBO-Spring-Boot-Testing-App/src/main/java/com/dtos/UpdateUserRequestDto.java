package com.dtos;

import lombok.Data;

@Data
public class UpdateUserRequestDto {

    private String email;
    private String username;
    private String password;
}
