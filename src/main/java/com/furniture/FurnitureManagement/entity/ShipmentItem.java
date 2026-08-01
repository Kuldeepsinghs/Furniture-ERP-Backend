package com.furniture.FurnitureManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shipment_items")
public class ShipmentItem {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "shipment_id",
            nullable = false)
    private Shipment shipment;

    @ManyToOne
    @JoinColumn(
            name = "design_id",
            nullable = false)
    private ProductDesign design;

    @Column(nullable = false)
    private Integer quantity;

    // Links this shipped line back to the exact production batch (worker +
    // work entry) it was fulfilled from. Nullable so existing/legacy shipment
    // rows created before this feature keep working without a batch link.
    @ManyToOne
    @JoinColumn(
            name = "work_entry_id",
            nullable = true)
    private WorkEntry workEntry;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public ProductDesign getDesign() {
		return design;
	}

	public void setDesign(ProductDesign design) {
		this.design = design;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public WorkEntry getWorkEntry() {
		return workEntry;
	}

	public void setWorkEntry(WorkEntry workEntry) {
		this.workEntry = workEntry;
	}

}