package com.furniture.FurnitureManagement.dto;

import java.time.LocalDateTime;

import com.furniture.FurnitureManagement.enums.FinishType;

public class WorkEntryRequest {

    private Long workerId;

    private Long designId;

    private Long rateTypeId;

    private Long handedToWorkerId;

    private String remarks;
    
    private Integer quantity;

    private FinishType finishType;

    public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public FinishType getFinishType() {
		return finishType;
	}

	public void setFinishType(FinishType finishType) {
		this.finishType = finishType;
	}

	private LocalDateTime workDateTime;

	public Long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}

	public Long getDesignId() {
		return designId;
	}

	public void setDesignId(Long designId) {
		this.designId = designId;
	}

	public Long getRateTypeId() {
		return rateTypeId;
	}

	public void setRateTypeId(Long rateTypeId) {
		this.rateTypeId = rateTypeId;
	}

	public Long getHandedToWorkerId() {
		return handedToWorkerId;
	}

	public void setHandedToWorkerId(Long handedToWorkerId) {
		this.handedToWorkerId = handedToWorkerId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	public LocalDateTime getWorkDateTime() {
		return workDateTime;
	}

	public void setWorkDateTime(LocalDateTime workDateTime) {
		this.workDateTime = workDateTime;
	}

    
}
