package com.controllers;

import com.dtos.ReservationDto;

import org.springframework.web.bind.annotation.*;

import com.services.impl.ReservationServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	private final ReservationServiceImpl reservationService;

	public ReservationController(ReservationServiceImpl reservationService) {
		this.reservationService = reservationService;
	}

	/**
	 * Crée une nouvelle réservation en BDD
	 */
	@PostMapping
	public ReservationDto saveReservation(final @RequestBody ReservationDto reservationDto){
		return reservationService.saveReservation(reservationDto);
	}

	/**
	 * Récupère toutes les réservations pour l'utilisateur dont l'id est fourni
	 */
	@GetMapping("/u/{user_id}")
	public List<ReservationDto> getReservationsByUserId(@PathVariable Long user_id){
		return reservationService.getReservationsByUserId(user_id);
	}

	/**
	 * Récupère une réservation par son id
	 */
	@GetMapping("/{id}")
	public ReservationDto getReservation(@PathVariable Long id){
		return reservationService.getReservationById(id);
	}
	
	/**
	 * Met à jour une réservation en BDD
	 */
	@PutMapping("/{id}")
	public ReservationDto updateReservation(@PathVariable Long id, final @RequestBody ReservationDto reservationDto){
		return reservationService.updateReservation(id, reservationDto);
	}

	/**
	 * Supprime une réservation par son id
	 */
	@DeleteMapping("/{id}")
	public boolean deleteReservation(@PathVariable Long id){
		return reservationService.deleteReservation(id);
	}
}
