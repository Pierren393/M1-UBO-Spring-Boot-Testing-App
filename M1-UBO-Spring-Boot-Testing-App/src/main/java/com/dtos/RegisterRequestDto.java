package com.dtos;

import lombok.Data;

@Data
public class RegisterRequestDto {

    private String email;
    private String password;
    private String username;
}
