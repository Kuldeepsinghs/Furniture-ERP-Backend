package com.furniture.FurnitureManagement.sales.dto;

import java.math.BigDecimal;

public class SalesReportResponse {

    private BigDecimal totalRevenue;

    private Long numberOfSales;

    private BigDecimal averageSale;

    private String topCategory;

    private String topLocation;

    public SalesReportResponse(
            BigDecimal totalRevenue,
            Long numberOfSales,
            BigDecimal averageSale,
            String topCategory,
            String topLocation) {

        this.totalRevenue = totalRevenue;
        this.numberOfSales = numberOfSales;
        this.averageSale = averageSale;
        this.topCategory = topCategory;
        this.topLocation = topLocation;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public Long getNumberOfSales() {
        return numberOfSales;
    }

    public BigDecimal getAverageSale() {
        return averageSale;
    }

    public String getTopCategory() {
        return topCategory;
    }

    public String getTopLocation() {
        return topLocation;
    }
}
