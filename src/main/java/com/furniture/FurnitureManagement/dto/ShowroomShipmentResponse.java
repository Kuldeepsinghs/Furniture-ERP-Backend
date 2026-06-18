package com.furniture.FurnitureManagement.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ShowroomShipmentResponse {

    private Long shipmentId;

    private LocalDateTime shipmentDateTime;

    private String remarks;

    private List<ShipmentItemDetailResponse> items;

    public ShowroomShipmentResponse(
            Long shipmentId,
            LocalDateTime shipmentDateTime,
            String remarks,
            List<ShipmentItemDetailResponse> items) {

        this.shipmentId = shipmentId;
        this.shipmentDateTime = shipmentDateTime;
        this.remarks = remarks;
        this.items = items;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public LocalDateTime getShipmentDateTime() {
        return shipmentDateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public List<ShipmentItemDetailResponse> getItems() {
        return items;
    }
}