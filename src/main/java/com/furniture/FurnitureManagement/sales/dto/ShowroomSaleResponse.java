package com.furniture.FurnitureManagement.sales.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ShowroomSaleResponse {

    private Long id;

    private String category;

    private String location;

    private String customerName;

    private String customerPhone;

    private List<ShowroomSaleProductResponse> products;

    private String remarks;

    private String description;

    private BigDecimal totalAmount;

    private LocalDateTime saleDateTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String status;

    public ShowroomSaleResponse() {
    }

    public ShowroomSaleResponse(
            Long id,
            String category,
            String location,
            String customerName,
            String customerPhone,
            List<ShowroomSaleProductResponse> products,
            String remarks,
            String description,
            BigDecimal totalAmount,
            LocalDateTime saleDateTime,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String createdBy,
            String status) {

        this.id = id;
        this.category = category;
        this.location = location;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.products = products;
        this.remarks = remarks;
        this.description = description;
        this.totalAmount = totalAmount;
        this.saleDateTime = saleDateTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public List<ShowroomSaleProductResponse> getProducts() {
        return products;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getSaleDateTime() {
        return saleDateTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getStatus() {
        return status;
    }
}
