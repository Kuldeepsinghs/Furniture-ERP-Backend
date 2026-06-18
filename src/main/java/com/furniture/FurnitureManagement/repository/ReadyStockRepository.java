package com.furniture.FurnitureManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.furniture.FurnitureManagement.entity.ProductDesign;
import com.furniture.FurnitureManagement.entity.ReadyStock;

public interface ReadyStockRepository
        extends JpaRepository<ReadyStock, Long> {

    Optional<ReadyStock>
    findByDesign(
            ProductDesign design);

    @Query("""
            SELECT COALESCE(SUM(r.availableQuantity),0)
            FROM ReadyStock r
            """)
    Long getTotalReadyStockQuantity();
}
