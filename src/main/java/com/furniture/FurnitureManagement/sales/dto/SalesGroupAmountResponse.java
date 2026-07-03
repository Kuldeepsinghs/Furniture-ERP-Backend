package com.furniture.FurnitureManagement.sales.dto;

import java.math.BigDecimal;

public class SalesGroupAmountResponse {

    private String name;

    private BigDecimal amount;

    public SalesGroupAmountResponse(
            String name,
            BigDecimal amount) {

        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
