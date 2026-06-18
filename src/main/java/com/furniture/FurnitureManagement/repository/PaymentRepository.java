package com.furniture.FurnitureManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.furniture.FurnitureManagement.entity.Payment;
import com.furniture.FurnitureManagement.entity.Worker;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    List<Payment> findByWorker(
            Worker worker);

    @Query("""
            SELECT p
            FROM Payment p
            WHERE p.cancelled = false OR p.cancelled IS NULL
            """)
    List<Payment> findByCancelledFalse();

    @Query("""
            SELECT p
            FROM Payment p
            WHERE p.worker = :worker
            AND (p.cancelled = false OR p.cancelled IS NULL)
            """)
    List<Payment> findByWorkerAndCancelledFalse(
            @Param("worker")
            Worker worker);
    
    List<Payment> findByWorkerId(
            Long workerId);

    @Query("""
            SELECT p
            FROM Payment p
            WHERE p.worker.id = :workerId
            AND (p.cancelled = false OR p.cancelled IS NULL)
            """)
    List<Payment> findByWorkerIdAndCancelledFalse(
            @Param("workerId")
            Long workerId);
    
    @Query("""
    		SELECT COALESCE(SUM(p.amount),0)
    		FROM Payment p
    		WHERE p.cancelled = false OR p.cancelled IS NULL
    		""")
    		BigDecimal getTotalPaid();
    
    @Query("""
    		SELECT COALESCE(SUM(p.amount),0)
    		FROM Payment p
    		WHERE p.worker.id = :workerId
            AND (p.cancelled = false OR p.cancelled IS NULL)
    		""")
    		BigDecimal getTotalPaidByWorker(
    		        @Param("workerId")
    		        Long workerId);
    
    
}
