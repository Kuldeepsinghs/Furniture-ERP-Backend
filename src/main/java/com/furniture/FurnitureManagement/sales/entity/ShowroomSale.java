package com.furniture.FurnitureManagement.sales.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "showroom_sales",
        indexes = {
                @Index(name = "idx_showroom_sales_sale_date_time", columnList = "saleDateTime"),
                @Index(name = "idx_showroom_sales_category", columnList = "category"),
                @Index(name = "idx_showroom_sales_location", columnList = "location")
        })
public class ShowroomSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String location;

    private String customerName;

    private String customerPhone;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "showroom_sale_products",
            joinColumns = @JoinColumn(name = "sale_id"))
    private List<ShowroomSaleProduct> products =
            new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime saleDateTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status = SaleStatus.ACTIVE;

    @PrePersist
    public void prePersist() {

        LocalDateTime now =
                LocalDateTime.now();

        if (saleDateTime == null) {

            saleDateTime = now;
        }

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {

        updatedAt =
                LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(
            String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(
            String location) {
        this.location = location;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(
            String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(
            String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public List<ShowroomSaleProduct> getProducts() {
        return products;
    }

    public void setProducts(
            List<ShowroomSaleProduct> products) {
        this.products =
                products == null
                ? new ArrayList<>()
                : products;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(
            String remarks) {
        this.remarks = remarks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {
        this.description = description;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(
            BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getSaleDateTime() {
        return saleDateTime;
    }

    public void setSaleDateTime(
            LocalDateTime saleDateTime) {
        this.saleDateTime = saleDateTime;
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

    public void setCreatedBy(
            String createdBy) {
        this.createdBy = createdBy;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(
            SaleStatus status) {
        this.status = status;
    }
}
