package com.furniture.FurnitureManagement.dto;

import java.math.BigDecimal;

public class WorkerLedgerResponse {

    private Long workerId;

    private String workerName;

    private BigDecimal totalEarned;

    private BigDecimal totalPaid;

    private BigDecimal balance;

    public WorkerLedgerResponse() {
    }

    public WorkerLedgerResponse(
            Long workerId,
            String workerName,
            BigDecimal totalEarned,
            BigDecimal totalPaid,
            BigDecimal balance) {

        this.workerId = workerId;
        this.workerName = workerName;
        this.totalEarned = totalEarned;
        this.totalPaid = totalPaid;
        this.balance = balance;
    }

	public Long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

    
}