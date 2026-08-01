package com.furniture.FurnitureManagement.dto;

public class ShipmentItemRequest {

    // The specific production batch (WorkEntry) this shipment line is
    // fulfilled from. This is what makes shipments traceable to a worker.
    private Long workEntryId;

    private Integer quantity;

	public Long getWorkEntryId() {
		return workEntryId;
	}

	public void setWorkEntryId(Long workEntryId) {
		this.workEntryId = workEntryId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


}