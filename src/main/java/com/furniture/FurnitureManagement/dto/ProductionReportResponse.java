package com.furniture.FurnitureManagement.dto;

public class ProductionReportResponse {

    private String designName;

    private Long totalQuantity;

    public ProductionReportResponse(
            String designName,
            Long totalQuantity) {

        this.designName = designName;
        this.totalQuantity = totalQuantity;
    }

    public String getDesignName() {
        return designName;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }
}