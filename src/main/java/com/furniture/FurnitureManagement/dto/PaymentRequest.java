package com.furniture.FurnitureManagement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.furniture.FurnitureManagement.enums.PaymentType;

public class PaymentRequest {

    private Long workerId;

    private BigDecimal amount;

    private PaymentType paymentType;

    private String remarks;

    private LocalDateTime paymentDateTime;

	public Long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
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

    
}
