package com.furniture.FurnitureManagement.dto;

import java.math.BigDecimal;

public class DashboardResponse {

    private Long totalWorkers;

    private Long totalShowrooms;

    private Long totalReadyStockItems;

    private Long totalShipments;

    private Long totalReadyStockQuantity;

    private BigDecimal totalEarnings;

    private BigDecimal totalPaid;

    private BigDecimal totalWorkerBalance;

    public DashboardResponse(
            Long totalWorkers,
            Long totalShowrooms,
            Long totalReadyStockItems,
            Long totalShipments,
            Long totalReadyStockQuantity,
            BigDecimal totalEarnings,
            BigDecimal totalPaid,
            BigDecimal totalWorkerBalance) {

        this.totalWorkers = totalWorkers;
        this.totalShowrooms = totalShowrooms;
        this.totalReadyStockItems = totalReadyStockItems;
        this.totalShipments = totalShipments;
        this.totalReadyStockQuantity = totalReadyStockQuantity;
        this.totalEarnings = totalEarnings;
        this.totalPaid = totalPaid;
        this.totalWorkerBalance = totalWorkerBalance;
    }

	public Long getTotalWorkers() {
		return totalWorkers;
	}

	public void setTotalWorkers(Long totalWorkers) {
		this.totalWorkers = totalWorkers;
	}

	public Long getTotalShowrooms() {
		return totalShowrooms;
	}

	public void setTotalShowrooms(Long totalShowrooms) {
		this.totalShowrooms = totalShowrooms;
	}

	public Long getTotalReadyStockItems() {
		return totalReadyStockItems;
	}

	public void setTotalReadyStockItems(Long totalReadyStockItems) {
		this.totalReadyStockItems = totalReadyStockItems;
	}

	public Long getTotalShipments() {
		return totalShipments;
	}

	public void setTotalShipments(Long totalShipments) {
		this.totalShipments = totalShipments;
	}

	public Long getTotalReadyStockQuantity() {
		return totalReadyStockQuantity;
	}

	public void setTotalReadyStockQuantity(Long totalReadyStockQuantity) {
		this.totalReadyStockQuantity = totalReadyStockQuantity;
	}

	public BigDecimal getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(BigDecimal totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public BigDecimal getTotalWorkerBalance() {
		return totalWorkerBalance;
	}

	public void setTotalWorkerBalance(BigDecimal totalWorkerBalance) {
		this.totalWorkerBalance = totalWorkerBalance;
	}

    
}
