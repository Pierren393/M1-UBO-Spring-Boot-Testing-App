package com.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthentificationDto {

	@NotBlank(message = "Le nom d'utilisateur est obligatoire")
	private String username;

	@NotBlank(message = "Le mot de passe est obligatoire")
	private String password;
	
}
