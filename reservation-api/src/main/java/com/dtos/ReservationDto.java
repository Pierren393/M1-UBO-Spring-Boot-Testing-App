package com.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReservationDto {
	
	@NotBlank(message = "L'identifiant de la réservation est obligatoire")
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@NotBlank(message = "La date de début de la réservation est obligatoire")
	private LocalDate start;

	public LocalDate getStart() {
		return this.start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}
	
	@NotBlank(message = "La date de fin de la réservation est obligatoire")
	private LocalDate end;

	public LocalDate getEnd() {
		return this.end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	@NotBlank(message = "L'identifiant du film de la réservation est obligatoire")
	private Long filmId;

	public Long getFilmId() {
		return this.filmId;
	}

	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}

	@NotBlank(message = "L'identifiant du paiement de la réservation est obligatoire")
	private Long paymentId;

	public Long getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	@NotBlank(message = "L'identifiant de l'utilisateur qui effectue la réservation est obligatoire")
	private Long userId;

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
