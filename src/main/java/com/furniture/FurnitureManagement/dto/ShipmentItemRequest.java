package com.furniture.FurnitureManagement.dto;

public class ShipmentItemRequest {

    private Long designId;

    private Integer quantity;

	public Long getDesignId() {
		return designId;
	}

	public void setDesignId(Long designId) {
		this.designId = designId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

  
}