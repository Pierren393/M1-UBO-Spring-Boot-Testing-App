package com.services.impl;

import com.dtos.PaymentDto;
import com.repositories.PaymentRepository;
import com.services.PaymentService;
import com.mappers.PaymentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Implémentation des opérations métier pour la gestion des paiements.
 * Cette classe suit le principe de Single Responsibility (SOLID).
 */
@Service("paymentService")
@Transactional
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final PaymentMapper paymentMapper;

	/**
	 * Constructeur avec injection des dépendances
	 * L'injection par constructeur est préférée à @Autowired car :
	 * - Elle rend les dépendances obligatoires
	 * - Elle facilite les tests unitaires
	 * - Elle permet l'immutabilité
	 */
	public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
		this.paymentRepository = paymentRepository;
		this.paymentMapper = paymentMapper;
	}

	/**
	 * {@inheritDoc}
	 * Cette méthode est transactionnelle par défaut grâce à @Transactional sur la classe
	 */
	@Override
	public PaymentDto savePayment(PaymentDto paymentDto) {
		var payment = paymentMapper.toEntity(paymentDto);
		var savedPayment = paymentRepository.save(payment);
		return paymentMapper.toDto(savedPayment);
	}

	/**
	 * {@inheritDoc}
	 * Utilisation de la méthode orElseThrow pour une gestion élégante des cas d'erreur
	 */
	@Override
	@Transactional(readOnly = true)
	public PaymentDto getPaymentById(Long paymentId) {
		var payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("Le paiement avec l'ID %d n'existe pas", paymentId)));
		return paymentMapper.toDto(payment);
	}

	/**
	 * {@inheritDoc}
	 * Utilisation de l'API Stream pour une transformation fonctionnelle des données
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PaymentDto> getAllPayments() {
		return paymentRepository.findAll().stream()
				.map(paymentMapper::toDto)
				.toList();
	}
}
