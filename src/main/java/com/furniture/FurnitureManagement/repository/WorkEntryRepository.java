package com.furniture.FurnitureManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.furniture.FurnitureManagement.dto.ProductionReportResponse;
import com.furniture.FurnitureManagement.entity.WorkEntry;
import com.furniture.FurnitureManagement.entity.Worker;
import com.furniture.FurnitureManagement.enums.ShipmentStatus;

public interface WorkEntryRepository
        extends JpaRepository<WorkEntry, Long> {

	List<WorkEntry> findByWorker(
	        Worker worker);

	@Query("""
	        SELECT w
	        FROM WorkEntry w
	        WHERE w.worker = :worker
	        AND (w.cancelled = false OR w.cancelled IS NULL)
	        """)
	List<WorkEntry> findByWorkerAndCancelledFalse(
	        @Param("worker")
	        Worker worker);
	
	List<WorkEntry> findByShipmentStatus(
	        ShipmentStatus shipmentStatus);

	@Query("""
	        SELECT w
	        FROM WorkEntry w
	        WHERE w.cancelled = false OR w.cancelled IS NULL
	        """)
	List<WorkEntry> findByCancelledFalse();

	@Query("""
	        SELECT w
	        FROM WorkEntry w
	        WHERE w.shipmentStatus = :shipmentStatus
	        AND (w.cancelled = false OR w.cancelled IS NULL)
	        """)
	List<WorkEntry> findByShipmentStatusAndCancelledFalse(
	        @Param("shipmentStatus")
	        ShipmentStatus shipmentStatus);
	
	@Query("""
			SELECT COALESCE(SUM(w.amount),0)
			FROM WorkEntry w
			WHERE w.cancelled = false OR w.cancelled IS NULL
			""")
	BigDecimal getTotalEarned();
	
	
	@Query("""
			SELECT COALESCE(SUM(w.amount),0)
			FROM WorkEntry w
			WHERE w.worker.id = :workerId
			AND (w.cancelled = false OR w.cancelled IS NULL)
			""")
			BigDecimal getTotalEarnedByWorker(
			        @Param("workerId")
			        Long workerId);
	
	@Query("""
			SELECT new com.furniture.FurnitureManagement.dto.ProductionReportResponse(
			    w.design.designName,
			    SUM(w.quantity)
			)
			FROM WorkEntry w
			WHERE w.worker.role = com.furniture.FurnitureManagement.enums.WorkerRole.POLISHER
			AND (w.cancelled = false OR w.cancelled IS NULL)
			GROUP BY w.design.designName
			ORDER BY SUM(w.quantity) DESC
			""")
			List<ProductionReportResponse>
			getProductionReport();
	
	@Query("""
	        SELECT w
	        FROM WorkEntry w
	        WHERE w.design.id = :designId
	        AND w.remainingQuantity > 0
	        AND (w.cancelled = false OR w.cancelled IS NULL)
	        ORDER BY w.workDateTime ASC
	        """)
	List<WorkEntry> findAvailableBatchesByDesign(
	        @Param("designId")
	        Long designId);

	@Query("""
	        SELECT DISTINCT w.design.id
	        FROM WorkEntry w
	        WHERE w.remainingQuantity > 0
	        AND (w.cancelled = false OR w.cancelled IS NULL)
	        """)
	List<Long> findDesignIdsWithAvailableBatches();

	@Query("""
	        SELECT w
	        FROM WorkEntry w
	        WHERE w.remainingQuantity > 0
	        AND (w.cancelled = false OR w.cancelled IS NULL)
	        ORDER BY w.workDateTime ASC
	        """)
	List<WorkEntry> findAllAvailableBatches();

	@Query("""
	        SELECT COALESCE(SUM(w.remainingQuantity),0)
	        FROM WorkEntry w
	        WHERE w.remainingQuantity > 0
	        AND (w.cancelled = false OR w.cancelled IS NULL)
	        """)
	Long getTotalAvailableQuantity();
}
