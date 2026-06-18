package com.furniture.FurnitureManagement.dto;

import java.math.BigDecimal;

public class PaymentSummaryResponse {

    private BigDecimal totalEarned;

    private BigDecimal totalPaid;

    private BigDecimal pendingAmount;

	public PaymentSummaryResponse(BigDecimal totalEarned, BigDecimal totalPaid, BigDecimal pendingAmount) {
		super();
		this.totalEarned = totalEarned;
		this.totalPaid = totalPaid;
		this.pendingAmount = pendingAmount;
	}

	public BigDecimal getTotalEarned() {
		return totalEarned;
	}

	public void setTotalEarned(BigDecimal totalEarned) {
		this.totalEarned = totalEarned;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public BigDecimal getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(BigDecimal pendingAmount) {
		this.pendingAmount = pendingAmount;
	}
    
    
}
