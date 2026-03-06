package com.mappers;

import com.dtos.PaymentDto;
import com.entities.Payment;
import org.springframework.stereotype.Component;

/**
 * Mapper responsable de la conversion entre les entités Payment et les DTOs PaymentDto.
 * Un mapper permet de séparer la couche de persistance de la couche de présentation.
 * 
 * Points clés du pattern Mapper :
 * - Conversion bidirectionnelle entre DTO et Entity
 * - Gestion des null-safety
 * - Pas de logique métier, uniquement de la transformation
 */
@Component
public class PaymentMapper {

    /**
     * Convertit une entité Payment en DTO PaymentDto
     * Cette méthode est utilisée pour exposer les données aux clients de l'API
     * 
     * @param payment l'entité à convertir
     * @return le DTO correspondant ou null si l'entité est null
     */
    public PaymentDto toDto(Payment payment) {
        if (payment == null) {
            return null;
        }
        
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setCurrency(payment.getCurrency());
        paymentDto.setStatus(payment.getStatus());
        paymentDto.setUserID(payment.getUserID());
        paymentDto.setCreatedAt(payment.getCreatedAt());
        return paymentDto;
    }

    /**
     * Convertit un DTO PaymentDto en entité Payment
     * Cette méthode est utilisée pour persister les données reçues des client
     * 
     * @param paymentDto le DTO à convertir
     * @return l'entité correspondante ou null si le DTO est null
     */
    public Payment toEntity(PaymentDto paymentDto) {
        if (paymentDto == null) {
            return null;
        }

        Payment payment = new Payment();
        // On ne set l'ID que s'il existe (cas d'une mise à jour)
        if (paymentDto.getId() != null) {
            payment.setId(paymentDto.getId());
        }
        payment.setAmount(paymentDto.getAmount());
        payment.setCurrency(paymentDto.getCurrency());
        payment.setStatus(paymentDto.getStatus());
        payment.setUserID(paymentDto.getUserID());
        payment.setCreatedAt(paymentDto.getCreatedAt());
        return payment;
    }
} 