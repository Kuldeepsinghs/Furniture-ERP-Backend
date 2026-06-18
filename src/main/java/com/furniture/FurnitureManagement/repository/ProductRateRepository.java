package com.furniture.FurnitureManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.FurnitureManagement.entity.ProductDesign;
import com.furniture.FurnitureManagement.entity.ProductRate;
import com.furniture.FurnitureManagement.entity.RateType;

public interface ProductRateRepository
        extends JpaRepository<ProductRate, Long> {

	Optional<ProductRate>
	findByDesignAndRateType(
	        ProductDesign design,
	        RateType rateType);

	Optional<ProductRate>
	findByDesignAndRateTypeAndActiveTrue(
	        ProductDesign design,
	        RateType rateType);

	java.util.List<ProductRate>
	findByActiveTrue();
}
