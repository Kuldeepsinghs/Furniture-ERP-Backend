package com.furniture.FurnitureManagement.sales.dto;

import java.math.BigDecimal;

public class ShowroomSaleProductResponse {

    private String productName;

    private String category;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal lineTotal;

    public ShowroomSaleProductResponse(
            String productName,
            String category,
            Integer quantity,
            BigDecimal price,
            BigDecimal lineTotal) {

        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.lineTotal = lineTotal;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
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