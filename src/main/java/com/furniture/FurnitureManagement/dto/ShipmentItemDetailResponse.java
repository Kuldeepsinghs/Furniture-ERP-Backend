package com.furniture.FurnitureManagement.dto;

public class ShipmentItemDetailResponse {

    private String designName;

    private Integer quantity;

    public ShipmentItemDetailResponse(
            String designName,
            Integer quantity) {

        this.designName = designName;
        this.quantity = quantity;
    }

    public String getDesignName() {
        return designName;
    }

    public Integer getQuantity() {
        return quantity;
    }
}