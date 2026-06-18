package com.furniture.FurnitureManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_designs")
public class ProductDesign {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String designName;

    @ManyToOne
    @JoinColumn(
            name = "category_id")
    private ProductCategory category;

    private boolean active = true;

    public ProductDesign() {
    }

	public ProductDesign(Long id, String designName, ProductCategory category, boolean active) {
		super();
		this.id = id;
		this.designName = designName;
		this.category = category;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

    
}