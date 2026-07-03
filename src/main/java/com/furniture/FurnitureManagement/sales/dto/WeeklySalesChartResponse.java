package com.furniture.FurnitureManagement.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WeeklySalesChartResponse {

    private LocalDate date;

    private BigDecimal amount;

    public WeeklySalesChartResponse(
            LocalDate date,
            BigDecimal amount) {

        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
