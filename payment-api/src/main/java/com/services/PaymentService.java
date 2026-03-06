package com.services;

import com.dtos.PaymentDto;

import java.util.List;

/**
 * Interface définissant les opérations métier disponibles pour la gestion des paiements.
 * Cette interface suit le principe d'Interface Segregation (SOLID).
 */
public interface PaymentService {
    /**
     * Sauvegarde un paiement dans le système
     * @param paymentDto les données du paiement à sauvegarder
     * @return le paiement sauvegardé avec son ID généré
     */
    PaymentDto savePayment(PaymentDto paymentDto);

    /**
     * Récupère un paiement par son identifiant
     * @param paymentId l'identifiant du paiement recherché
     * @return le paiement trouvé
     * @throws jakarta.persistence.EntityNotFoundException si le paiement n'existe pas
     */
    PaymentDto getPaymentById(Long paymentId);

    /**
     * Récupère tous les paiements du système
     * @return la liste des paiements
     */
    List<PaymentDto> getAllPayments();
}
