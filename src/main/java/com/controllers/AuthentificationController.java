package com.controllers;

import com.dtos.AuthentificationDto;
import org.springframework.web.bind.annotation.*;

import com.services.impl.AuthentificationServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/authentifications")
public class AuthentificationController {
	
	private final AuthentificationServiceImpl authentificationService;

	public AuthentificationController(AuthentificationServiceImpl authentificationService) {
		this.authentificationService = authentificationService;
	}

	/**
	 * <p>Get all authentifications in the system</p>
	 * @return List<AuthentificationDto>
	 */
	@GetMapping
	public List<AuthentificationDto> getAuthentifications() {
		return authentificationService.getAllAuthentifications();
	}

	/**
	 * Method to get the authentification based on the ID
	 */
	@GetMapping("/{id}")
	public AuthentificationDto getAuthentification(@PathVariable Long id){
		return authentificationService.getAuthentificationById(id);
	}

	/**
	 * Create a new Authentification in the system
	 */
	@PostMapping
	public AuthentificationDto saveAuthentification(final @RequestBody AuthentificationDto authentificationDto){
		return authentificationService.saveAuthentification(authentificationDto);
	}

	/**
	 * Delete a authentification by it's id
	 */
	@DeleteMapping("/{id}")
	public Boolean deleteAuthentification(@PathVariable Long id){
		return authentificationService.deleteAuthentification(id);
	}
	
}
