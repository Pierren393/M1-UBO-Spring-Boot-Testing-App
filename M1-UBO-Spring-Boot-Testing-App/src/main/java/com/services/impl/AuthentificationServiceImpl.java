package com.services.impl;

import com.dtos.AuthentificationDto;
import com.entities.Authentification;
import com.repositories.AuthentificationRepository;
import com.services.AuthentificationService;
import com.mappers.AuthentificationMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Implémentation des opérations métier pour la gestion des chiens.
 * Cette classe suit le principe de Single Responsibility (SOLID).
 */
@Service("authentificationService")
@Transactional
public class AuthentificationServiceImpl implements AuthentificationService {

	private final AuthentificationRepository authentificationRepository;
	private final AuthentificationMapper authentificationMapper;

	/**
	 * Constructeur avec injection des dépendances
	 * L'injection par constructeur est préférée à @Autowired car :
	 * - Elle rend les dépendances obligatoires
	 * - Elle facilite les tests unitaires
	 * - Elle permet l'immutabilité
	 */
	public AuthentificationServiceImpl(AuthentificationRepository authentificationRepository,
			AuthentificationMapper authentificationMapper) {
		this.authentificationRepository = authentificationRepository;
		this.authentificationMapper = authentificationMapper;
	}

	/**
	 * {@inheritDoc}
	 * Cette méthode est transactionnelle par défaut grâce à @Transactional sur la
	 * classe
	 */
	@Override
	public AuthentificationDto saveAuthentification(AuthentificationDto authentificationDto) {
		var authentification = authentificationMapper.toEntity(authentificationDto);
		var savedAuthentification = authentificationRepository.save(authentification);
		return authentificationMapper.toDto(savedAuthentification);
	}

	/**
	 * {@inheritDoc}
	 * Utilisation de la méthode orElseThrow pour une gestion élégante des cas
	 * d'erreur
	 */
	@Override
	@Transactional(readOnly = true)
	public AuthentificationDto getAuthentificationById(Long authentificationId) {
		var authentification = authentificationRepository.findById(authentificationId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("Le chien avec l'ID %d n'existe pas", authentificationId)));
		return authentificationMapper.toDto(authentification);
	}

	/**
	 * {@inheritDoc}
	 * La méthode deleteById ne lève pas d'exception si l'entité n'existe pas
	 */
	@Override
	public boolean deleteAuthentification(Long authentificationId) {
		authentificationRepository.deleteById(authentificationId);
		return true;
	}

	/**
	 * {@inheritDoc}
	 * Utilisation de l'API Stream pour une transformation fonctionnelle des données
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AuthentificationDto> getAllAuthentifications() {
		return authentificationRepository.findAll().stream()
				.map(authentificationMapper::toDto)
				.toList();
	}
}
