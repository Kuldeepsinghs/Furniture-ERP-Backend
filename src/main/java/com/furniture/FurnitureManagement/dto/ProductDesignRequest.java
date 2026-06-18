package com.furniture.FurnitureManagement.dto;

public class ProductDesignRequest {

    private String designName;

    private Long categoryId;

    public ProductDesignRequest() {
    }

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

    
}