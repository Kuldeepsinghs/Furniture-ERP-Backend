package com.furniture.FurnitureManagement.sales.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.furniture.FurnitureManagement.sales.entity.SaleStatus;
import com.furniture.FurnitureManagement.sales.entity.ShowroomSale;

public interface ShowroomSaleRepository
        extends JpaRepository<ShowroomSale, Long>,
        JpaSpecificationExecutor<ShowroomSale> {

    List<ShowroomSale>
    findByStatusOrderBySaleDateTimeDesc(
            SaleStatus status,
            Pageable pageable);

    @Query("""
            SELECT COALESCE(SUM(s.totalAmount),0)
            FROM ShowroomSale s
            WHERE s.status = com.furniture.FurnitureManagement.sales.entity.SaleStatus.ACTIVE
            AND s.saleDateTime >= :start
            AND s.saleDateTime < :end
            """)
    BigDecimal getTotalAmountBetween(
            @Param("start")
            LocalDateTime start,
            @Param("end")
            LocalDateTime end);

    @Query("""
            SELECT COUNT(s)
            FROM ShowroomSale s
            WHERE s.status = com.furniture.FurnitureManagement.sales.entity.SaleStatus.ACTIVE
            AND s.saleDateTime >= :start
            AND s.saleDateTime < :end
            """)
    Long countSalesBetween(
            @Param("start")
            LocalDateTime start,
            @Param("end")
            LocalDateTime end);

    @Query("""
            SELECT new com.furniture.FurnitureManagement.sales.dto.SalesGroupAmountResponse(
                s.category,
                COALESCE(SUM(s.totalAmount),0)
            )
            FROM ShowroomSale s
            WHERE s.status = com.furniture.FurnitureManagement.sales.entity.SaleStatus.ACTIVE
            AND s.saleDateTime >= :start
            AND s.saleDateTime < :end
            GROUP BY s.category
            ORDER BY SUM(s.totalAmount) DESC
            """)
    List<com.furniture.FurnitureManagement.sales.dto.SalesGroupAmountResponse>
    getTopCategoriesBetween(
            @Param("start")
            LocalDateTime start,
            @Param("end")
            LocalDateTime end,
            Pageable pageable);

    @Query("""
            SELECT new com.furniture.FurnitureManagement.sales.dto.SalesGroupAmountResponse(
                s.location,
                COALESCE(SUM(s.totalAmount),0)
            )
            FROM ShowroomSale s
            WHERE s.status = com.furniture.FurnitureManagement.sales.entity.SaleStatus.ACTIVE
            AND s.saleDateTime >= :start
            AND s.saleDateTime < :end
            GROUP BY s.location
            ORDER BY SUM(s.totalAmount) DESC
            """)
    List<com.furniture.FurnitureManagement.sales.dto.SalesGroupAmountResponse>
    getTopLocationsBetween(
            @Param("start")
            LocalDateTime start,
            @Param("end")
            LocalDateTime end,
            Pageable pageable);
}
