package com.furniture.FurnitureManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.furniture.FurnitureManagement.entity.ProductCategory;

public interface ProductCategoryRepository
        extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findByActiveTrue();
}
