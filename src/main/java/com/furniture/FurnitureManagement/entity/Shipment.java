package com.furniture.FurnitureManagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "showroom_id",
            nullable = false)
    private Showroom showroom;

    private LocalDateTime shipmentDateTime;

    private String remarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Showroom getShowroom() {
		return showroom;
	}

	public void setShowroom(Showroom showroom) {
		this.showroom = showroom;
	}

	public LocalDateTime getShipmentDateTime() {
		return shipmentDateTime;
	}

	public void setShipmentDateTime(LocalDateTime shipmentDateTime) {
		this.shipmentDateTime = shipmentDateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    
}