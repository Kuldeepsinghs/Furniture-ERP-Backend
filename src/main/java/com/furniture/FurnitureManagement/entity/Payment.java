package com.furniture.FurnitureManagement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.furniture.FurnitureManagement.enums.PaymentType;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "worker_id",
            nullable = false)
    private Worker worker;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String remarks;

    private LocalDateTime paymentDateTime;

    private Boolean cancelled = false;

    public Payment() {
    }

	public Payment(Long id, Worker worker, BigDecimal amount, PaymentType paymentType, String remarks,
			LocalDateTime paymentDateTime, boolean cancelled) {
		super();
		this.id = id;
		this.worker = worker;
		this.amount = amount;
		this.paymentType = paymentType;
		this.remarks = remarks;
		this.paymentDateTime = paymentDateTime;
		this.cancelled = cancelled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getPaymentDateTime() {
		return paymentDateTime;
	}

	public void setPaymentDateTime(LocalDateTime paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}

	public boolean isCancelled() {
		return Boolean.TRUE.equals(cancelled);
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

    
}
