package com.furniture.FurnitureManagement.sales.dto;

import java.math.BigDecimal;

public class ShowroomSaleProductResponse {

    private String productName;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal lineTotal;

    public ShowroomSaleProductResponse(
            String productName,
            Integer quantity,
            BigDecimal price,
            BigDecimal lineTotal) {

        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.lineTotal = lineTotal;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }
}
