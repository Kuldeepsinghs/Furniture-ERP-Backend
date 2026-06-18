package com.furniture.FurnitureManagement.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ShipmentHistoryResponse {

    private Long id;

    private String showroomName;

    private LocalDateTime shipmentDateTime;

    private String remarks;

    private List<ShipmentItemDetailResponse> items;
    
    public ShipmentHistoryResponse() {
    }
    
    public ShipmentHistoryResponse(Long id, String showroomName, LocalDateTime shipmentDateTime, String remarks,
    		List<ShipmentItemDetailResponse> items) {
    	super();
    	this.id = id;
    	this.showroomName = showroomName;
    	this.shipmentDateTime = shipmentDateTime;
    	this.remarks = remarks;
    	this.items = items;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShowroomName() {
		return showroomName;
	}

	public void setShowroomName(String showroomName) {
		this.showroomName = showroomName;
	}

	public LocalDateTime getShipmentDateTime() {
		return shipmentDateTime;
	}

	public void setShipmentDateTime(LocalDateTime shipmentDateTime) {
		this.shipmentDateTime = shipmentDateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<ShipmentItemDetailResponse> getItems() {
		return items;
	}

	public void setItems(List<ShipmentItemDetailResponse> items) {
		this.items = items;
	}

    
}
