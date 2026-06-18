package com.furniture.FurnitureManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.FurnitureManagement.entity.Worker;
import com.furniture.FurnitureManagement.enums.Status;

public interface WorkerRepository
        extends JpaRepository<Worker, Long> {
	
	List<Worker> findByNameContainingIgnoreCase(
	        String name);

	List<Worker> findByStatus(
	        Status status);

	List<Worker> findByNameContainingIgnoreCaseAndStatus(
	        String name,
	        Status status);

	Long countByStatus(
	        Status status);

}
