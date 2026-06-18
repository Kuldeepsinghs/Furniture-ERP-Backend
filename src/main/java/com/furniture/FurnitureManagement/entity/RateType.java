package com.furniture.FurnitureManagement.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "rate_types")
public class RateType {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true)
    private String name;

    private BigDecimal defaultCarpenterRate;

    private BigDecimal defaultPolisherRate;

    private BigDecimal walnutExtra = BigDecimal.ZERO;

    private boolean active = true;

    public RateType() {
    }

	public RateType(Long id, String name, BigDecimal defaultCarpenterRate, BigDecimal defaultPolisherRate,
			BigDecimal walnutExtra, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.defaultCarpenterRate = defaultCarpenterRate;
		this.defaultPolisherRate = defaultPolisherRate;
		this.walnutExtra = walnutExtra;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getDefaultCarpenterRate() {
		return defaultCarpenterRate;
	}

	public void setDefaultCarpenterRate(BigDecimal defaultCarpenterRate) {
		this.defaultCarpenterRate = defaultCarpenterRate;
	}

	public BigDecimal getDefaultPolisherRate() {
		return defaultPolisherRate;
	}

	public void setDefaultPolisherRate(BigDecimal defaultPolisherRate) {
		this.defaultPolisherRate = defaultPolisherRate;
	}

	public BigDecimal getWalnutExtra() {
		return walnutExtra;
	}

	public void setWalnutExtra(BigDecimal walnutExtra) {
		this.walnutExtra = walnutExtra;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

    
}
