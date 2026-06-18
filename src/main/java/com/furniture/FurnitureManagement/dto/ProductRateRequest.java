package com.furniture.FurnitureManagement.dto;

import java.math.BigDecimal;

public class ProductRateRequest {

    private Long designId;

    private Long rateTypeId;

    private BigDecimal amount;

    private BigDecimal carpenterRate;

    private BigDecimal polisherRate;

    public ProductRateRequest() {
    }

	public Long getDesignId() {
		return designId;
	}

	public void setDesignId(Long designId) {
		this.designId = designId;
	}

	public Long getRateTypeId() {
		return rateTypeId;
	}

	public void setRateTypeId(Long rateTypeId) {
		this.rateTypeId = rateTypeId;
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

    
}
