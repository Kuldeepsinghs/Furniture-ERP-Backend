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

    /**
     * Category is now tracked per product line (a single sale can mix
     * categories, e.g. a sofa + a cot). This aggregates revenue per
     * category across product lines, case-insensitively (so "Cot" and
     * "cot" are treated as the same category), joined against the parent
     * sale for date-range and status filtering.
     */
    @Query(value = """
            SELECT INITCAP(MIN(TRIM(p.category))) AS label,
                   COALESCE(SUM(p.price * p.quantity), 0) AS amount
            FROM showroom_sale_products p
            JOIN showroom_sales s ON s.id = p.sale_id
            WHERE s.status = 'ACTIVE'
              AND s.sale_date_time >= :start
              AND s.sale_date_time < :end
              AND p.category IS NOT NULL
              AND TRIM(p.category) <> ''
            GROUP BY LOWER(TRIM(p.category))
            ORDER BY amount DESC
            """,
            nativeQuery = true)
    List<com.furniture.FurnitureManagement.sales.dto.CategoryAmountProjection>
    getTopProductCategoriesBetween(
            @Param("start")
            LocalDateTime start,
            @Param("end")
            LocalDateTime end,
            Pageable pageable);

    @Query("""
            SELECT new com.furniture.FurnitureManagement.sales.dto.SalesGroupAmountResponse(
                MIN(s.location),
                COALESCE(SUM(s.totalAmount),0)
            )
            FROM ShowroomSale s
            WHERE s.status = com.furniture.FurnitureManagement.sales.entity.SaleStatus.ACTIVE
            AND s.saleDateTime >= :start
            AND s.saleDateTime < :end
            GROUP BY LOWER(TRIM(s.location))
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