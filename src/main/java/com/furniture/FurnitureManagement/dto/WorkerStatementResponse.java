package com.furniture.FurnitureManagement.dto;

import java.math.BigDecimal;

public class WorkerStatementResponse {

    private Long workerId;
    private String workerName;
    private String role;

    private BigDecimal totalEarned;
    private BigDecimal totalPaid;
    private BigDecimal balance;

    public WorkerStatementResponse(
            Long workerId,
            String workerName,
            String role,
            BigDecimal totalEarned,
            BigDecimal totalPaid,
            BigDecimal balance) {

        this.workerId = workerId;
        this.workerName = workerName;
        this.role = role;
        this.totalEarned = totalEarned;
        this.totalPaid = totalPaid;
        this.balance = balance;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getRole() {
        return role;
    }

    public BigDecimal getTotalEarned() {
        return totalEarned;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}