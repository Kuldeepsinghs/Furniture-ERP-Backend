package com.furniture.FurnitureManagement.dto;

import java.math.BigDecimal;

public class RateTypeRequest {

    private String name;

    private BigDecimal defaultCarpenterRate;

    private BigDecimal defaultPolisherRate;

    private BigDecimal walnutExtra;

    public RateTypeRequest() {
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

    
}
