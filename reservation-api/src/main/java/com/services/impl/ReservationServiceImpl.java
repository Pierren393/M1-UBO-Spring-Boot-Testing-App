package com.services.impl;

import com.dtos.ReservationDto;
import com.repositories.ReservationRepository;
import com.services.ReservationService;
import com.mappers.ReservationMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Implémentation des opérations métier pour la gestion des paiements.
 * Cette classe suit le principe de Single Responsibility (SOLID).
 */
@Service("reservationService")
@Transactional
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;
	private final ReservationMapper reservationMapper;

	/**
	 * Constructeur avec injection des dépendances
	 * L'injection par constructeur est préférée à @Autowired car :
	 * - Elle rend les dépendances obligatoires
	 * - Elle facilite les tests unitaires
	 * - Elle permet l'immutabilité
	 */
	public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
		this.reservationRepository = reservationRepository;
		this.reservationMapper = reservationMapper;
	}

	/**
	 * {@inheritDoc}
	 * Cette méthode est transactionnelle par défaut grâce à @Transactional sur la classe
	 */
	@Override
	public ReservationDto saveReservation(ReservationDto reservationDto) {
		var reservation = reservationMapper.toEntity(reservationDto);
		var savedReservation = reservationRepository.save(reservation);
		return reservationMapper.toDto(savedReservation);
	}

	/**
	 * {@inheritDoc}
	 * Utilisation de la méthode orElseThrow pour une gestion élégante des cas d'erreur
	 */
	@Override
	@Transactional(readOnly = true)
	public ReservationDto getReservationById(Long reservationId) {
		var reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("La reservation avec l'ID %d n'existe pas", reservationId)));
		return reservationMapper.toDto(reservation);
	}

	/**
	 * {@inheritDoc}
	 * Utilisation de l'API Stream pour une transformation fonctionnelle des données
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ReservationDto> getReservationsByUserId(Long userId) {
		return reservationRepository.findByUserId(userId)
				.stream()
				.map(reservationMapper::toDto)
				.toList();
	}
    
	/**
	 * {@inheritDoc}
	 * La méthode deleteById ne lève pas d'exception si l'entité n'existe pas
	 */
	@Override
	public boolean deleteReservation(Long id) {
		reservationRepository.deleteById(id);
		return true;
	}

	@Override
    public ReservationDto updateReservation(Long id, ReservationDto reservationDto){
		reservationDto.setId(id);
		var reservation = reservationMapper.toEntity(reservationDto);
		var updatedReservation = reservationRepository.save(reservation);
		return reservationMapper.toDto(updatedReservation);
	}
	
}
