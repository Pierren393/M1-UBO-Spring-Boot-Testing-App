package com.controllers;

import com.dtos.PaymentDto;
import org.springframework.web.bind.annotation.*;

import com.services.impl.PaymentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	private final PaymentServiceImpl paymentService;

	public PaymentController(PaymentServiceImpl paymentService) {
		this.paymentService = paymentService;
	}

	/**
	 * <p>Obtient la liste de tous les paiements</p>
	 * @return List<PaymentDto>
	 */
	@GetMapping
	public List<PaymentDto> getPayments() {
		return paymentService.getAllPayments();
	}

	/**
	 * Crée un nouveau paiement en BDD
	 */
	@PostMapping
	public PaymentDto savePayment(final @RequestBody PaymentDto paymentDto){
		return paymentService.savePayment(paymentDto);
	}

	/**
	 * Récupère un paiement par son id
	 */
	@GetMapping("/{id}")
	public PaymentDto getPayment(@PathVariable Long id){
		return paymentService.getPaymentById(id);
	}
}
