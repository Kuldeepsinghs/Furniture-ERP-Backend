package com.furniture.FurnitureManagement.sales.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalesDashboardResponse {

    private BigDecimal todaySalesAmount;

    private BigDecimal weeklySalesAmount;

    private BigDecimal monthlySalesAmount;

    private BigDecimal yearlySalesAmount;

    private List<ShowroomSaleResponse> recentSales;

    private List<SalesGroupAmountResponse> topSellingCategories;

    private List<SalesGroupAmountResponse> topSellingLocations;

    private List<WeeklySalesChartResponse> weeklySalesChart;

    public SalesDashboardResponse(
            BigDecimal todaySalesAmount,
            BigDecimal weeklySalesAmount,
            BigDecimal monthlySalesAmount,
            BigDecimal yearlySalesAmount,
            List<ShowroomSaleResponse> recentSales,
            List<SalesGroupAmountResponse> topSellingCategories,
            List<SalesGroupAmountResponse> topSellingLocations,
            List<WeeklySalesChartResponse> weeklySalesChart) {

        this.todaySalesAmount = todaySalesAmount;
        this.weeklySalesAmount = weeklySalesAmount;
        this.monthlySalesAmount = monthlySalesAmount;
        this.yearlySalesAmount = yearlySalesAmount;
        this.recentSales = recentSales;
        this.topSellingCategories = topSellingCategories;
        this.topSellingLocations = topSellingLocations;
        this.weeklySalesChart = weeklySalesChart;
    }

    public BigDecimal getTodaySalesAmount() {
        return todaySalesAmount;
    }

    public BigDecimal getWeeklySalesAmount() {
        return weeklySalesAmount;
    }

    public BigDecimal getMonthlySalesAmount() {
        return monthlySalesAmount;
    }

    public BigDecimal getYearlySalesAmount() {
        return yearlySalesAmount;
    }

    public List<ShowroomSaleResponse> getRecentSales() {
        return recentSales;
    }

    public List<SalesGroupAmountResponse> getTopSellingCategories() {
        return topSellingCategories;
    }

    public List<SalesGroupAmountResponse> getTopSellingLocations() {
        return topSellingLocations;
    }

    public List<WeeklySalesChartResponse> getWeeklySalesChart() {
        return weeklySalesChart;
    }
}
