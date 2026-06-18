package com.furniture.FurnitureManagement.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "product_rates")
public class ProductRate {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "design_id",
            nullable = false)
    private ProductDesign design;

    @ManyToOne
    @JoinColumn(
            name = "rate_type_id",
            nullable = false)
    private RateType rateType;

    @Column(
            nullable = true)
    private BigDecimal amount;

    private BigDecimal carpenterRate;

    private BigDecimal polisherRate;

    private boolean active = true;

    public ProductRate() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductDesign getDesign() {
		return design;
	}

	public void setDesign(ProductDesign design) {
		this.design = design;
	}

	public RateType getRateType() {
		return rateType;
	}

	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCarpenterRate() {
		return carpenterRate;
	}

	public void setCarpenterRate(BigDecimal carpenterRate) {
		this.carpenterRate = carpenterRate;
	}

	public BigDecimal getPolisherRate() {
		return polisherRate;
	}

	public void setPolisherRate(BigDecimal polisherRate) {
		this.polisherRate = polisherRate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ProductRate(Long id, ProductDesign design, RateType rateType, BigDecimal amount, BigDecimal carpenterRate,
			BigDecimal polisherRate, boolean active) {
		super();
		this.id = id;
		this.design = design;
		this.rateType = rateType;
		this.amount = amount;
		this.carpenterRate = carpenterRate;
		this.polisherRate = polisherRate;
		this.active = active;
	}

    
}
