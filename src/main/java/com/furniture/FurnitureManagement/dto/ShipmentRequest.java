package com.furniture.FurnitureManagement.dto;

import java.util.List;

public class ShipmentRequest {

    private Long showroomId;

    private String remarks;

    private List<ShipmentItemRequest>
            items;

	public Long getShowroomId() {
		return showroomId;
	}

	public void setShowroomId(Long showroomId) {
		this.showroomId = showroomId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<ShipmentItemRequest> getItems() {
		return items;
	}

	public void setItems(List<ShipmentItemRequest> items) {
		this.items = items;
	}
    
}