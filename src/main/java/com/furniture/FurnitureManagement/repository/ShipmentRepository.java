package com.furniture.FurnitureManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.furniture.FurnitureManagement.entity.Shipment;
import com.furniture.FurnitureManagement.entity.Showroom;

public interface ShipmentRepository
        extends JpaRepository<Shipment, Long> {

	List<Shipment> findByShowroom(
	        Showroom showroom);
	
	List<Shipment>
	findByShowroomIdOrderByShipmentDateTimeDesc(
	        Long showroomId);
}