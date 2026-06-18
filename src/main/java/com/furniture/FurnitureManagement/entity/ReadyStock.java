package com.furniture.FurnitureManagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "ready_stock")
public class ReadyStock {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "design_id",
            nullable = false,
            unique = true)
    private ProductDesign design;

    @Column(nullable = false)
    private Integer availableQuantity = 0;

    private LocalDateTime lastUpdated;

    public ReadyStock() {
    }

    public Long getId() {
        return id;
    }

    public ProductDesign getDesign() {
        return design;
    }

    public void setDesign(
            ProductDesign design) {
        this.design = design;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(
            Integer availableQuantity) {
        this.availableQuantity =
                availableQuantity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(
            LocalDateTime lastUpdated) {
        this.lastUpdated =
                lastUpdated;
    }
}