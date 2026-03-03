package com.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthentificationDto {

	private Long id;

	@NotBlank(message = "Le nom est obligatoire")
	private String name;

	@NotBlank(message = "La race est obligatoire")
	private String race;
}
