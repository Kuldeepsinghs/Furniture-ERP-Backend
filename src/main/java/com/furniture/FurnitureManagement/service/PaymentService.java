package com.furniture.FurnitureManagement.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.PaymentRequest;
import com.furniture.FurnitureManagement.dto.PaymentSummaryResponse;
import com.furniture.FurnitureManagement.entity.Payment;
import com.furniture.FurnitureManagement.entity.Worker;
import com.furniture.FurnitureManagement.repository.PaymentRepository;
import com.furniture.FurnitureManagement.repository.WorkEntryRepository;
import com.furniture.FurnitureManagement.repository.WorkerRepository;

@Service
public class PaymentService {

    private final PaymentRepository
            paymentRepository;

    private final WorkerRepository
            workerRepository;
    
    private final WorkEntryRepository workEntryRepository;

    
    public PaymentService(PaymentRepository paymentRepository, WorkerRepository workerRepository,
			WorkEntryRepository workEntryRepository) {
		super();
		this.paymentRepository = paymentRepository;
		this.workerRepository = workerRepository;
		this.workEntryRepository = workEntryRepository;
	}

	public Payment addPayment(
            PaymentRequest request) {

        Worker worker =
                workerRepository
                .findById(
                        request.getWorkerId())
                .orElseThrow();

        Payment payment =
                new Payment();

        payment.setWorker(worker);

        payment.setAmount(
                request.getAmount());

        payment.setPaymentType(
                request.getPaymentType());

        payment.setRemarks(
                request.getRemarks());

        payment.setPaymentDateTime(
                request.getPaymentDateTime() != null
                ? request.getPaymentDateTime()
                : LocalDateTime.now());

        return paymentRepository.save(
                payment);
    }

    public List<Payment> getAllPayments() {

        return paymentRepository.findByCancelledFalse();
    }
    
    public PaymentSummaryResponse
    getPaymentSummary() {

        BigDecimal totalEarned =
                workEntryRepository
                        .getTotalEarned();

        BigDecimal totalPaid =
                paymentRepository
                        .getTotalPaid();

        return new PaymentSummaryResponse(

                totalEarned,

                totalPaid,

                totalEarned.subtract(
                        totalPaid));
    }
    
    public List<Payment>
    getPaymentsByWorker(
            Long workerId) {

        workerRepository
                .findById(workerId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Worker not found"));

        return paymentRepository
                .findByWorkerIdAndCancelledFalse(
                        workerId);
    }
    
    public Payment updatePayment(
            Long id,
            PaymentRequest request) {

        Payment payment =
                paymentRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Payment not found"));

        if (payment.isCancelled()) {

            throw new RuntimeException(
                    "Unable to Update");
        }

        if (request.getAmount()
                != null) {

            payment.setAmount(
                    request.getAmount());
        }

        payment.setRemarks(
                request.getRemarks());

        if (request.getPaymentDateTime()
                != null) {

            payment.setPaymentDateTime(
                    request.getPaymentDateTime());
        }

        return paymentRepository
                .save(payment);
    }

    public Payment cancelPayment(
            Long id) {

        Payment payment =
                paymentRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Payment not found"));

        if (payment.isCancelled()) {

            return payment;
        }

        payment.setCancelled(true);

        return paymentRepository
                .save(payment);
    }
}
