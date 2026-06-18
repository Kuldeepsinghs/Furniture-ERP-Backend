package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.FurnitureManagement.dto.PaymentRequest;
import com.furniture.FurnitureManagement.dto.PaymentSummaryResponse;
import com.furniture.FurnitureManagement.entity.Payment;
import com.furniture.FurnitureManagement.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService
            paymentService;

    public PaymentController(
            PaymentService paymentService) {

        this.paymentService =
                paymentService;
    }

    @PostMapping
    public Payment addPayment(
            @RequestBody
            PaymentRequest request) {

        return paymentService
                .addPayment(request);
    }

    @GetMapping
    public List<Payment> getAllPayments() {

        return paymentService
                .getAllPayments();
    }
    
    @GetMapping("/summary")
    public PaymentSummaryResponse
    getSummary() {

        return paymentService
                .getPaymentSummary();
    }
    
    @GetMapping("/worker/{workerId}")
    public List<Payment>
    getPaymentsByWorker(
            @PathVariable
            Long workerId) {

        return paymentService
                .getPaymentsByWorker(
                        workerId);
    }
    
    @PutMapping("/{id}")
    public Payment updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentRequest request) {

        return paymentService
                .updatePayment(
                        id,
                        request);
    }

    @PatchMapping("/{id}/cancel")
    public Payment cancelPayment(
            @PathVariable Long id) {

        return paymentService
                .cancelPayment(id);
    }
}

