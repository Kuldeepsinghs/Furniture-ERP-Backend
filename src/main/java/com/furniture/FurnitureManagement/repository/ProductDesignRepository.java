package com.furniture.FurnitureManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.furniture.FurnitureManagement.entity.ProductDesign;

public interface ProductDesignRepository
        extends JpaRepository<ProductDesign, Long> {

    List<ProductDesign> findByActiveTrue();
}
