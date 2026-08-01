package com.furniture.FurnitureManagement.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.furniture.FurnitureManagement.enums.FinishType;
import com.furniture.FurnitureManagement.enums.ShipmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "work_entries")
public class WorkEntry {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "worker_id",
            nullable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(
            name = "design_id",
            nullable = false)
    private ProductDesign design;

    @ManyToOne
    @JoinColumn(
            name = "rate_type_id",
            nullable = false)
    private RateType rateType;
    
	
	@Column(nullable = false)
	private BigDecimal unitRate;

    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private Integer quantity = 1;

    @Enumerated(EnumType.STRING)
    private FinishType finishType = FinishType.NORMAL;
    
    private LocalDateTime workDateTime;
    
    @ManyToOne
    @JoinColumn(
    		name = "handed_to_worker_id")
    private Worker handedToWorker;
    
    @Enumerated(EnumType.STRING)
    private ShipmentStatus shipmentStatus;
    
    private Integer remainingQuantity;
    
    private String remarks;

    private Boolean cancelled = false;
    
    public BigDecimal getUnitRate() {
		return unitRate;
	}

	public void setUnitRate(BigDecimal unitRate) {
		this.unitRate = unitRate;
	}

	public WorkEntry() {
    }

    public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

    public FinishType getFinishType() {
		return finishType;
	}

	public void setFinishType(FinishType finishType) {
		this.finishType = finishType;
	}


	public WorkEntry(Long id, Worker worker, ProductDesign design, RateType rateType, BigDecimal amount,
			Integer quantity, FinishType finishType, LocalDateTime workDateTime, Worker handedToWorker,
			ShipmentStatus shipmentStatus, String remarks, boolean cancelled) {
		super();
		this.id = id;
		this.worker = worker;
		this.design = design;
		this.rateType = rateType;
		this.amount = amount;
		this.quantity = quantity;
		this.finishType = finishType;
		this.workDateTime = workDateTime;
		this.handedToWorker = handedToWorker;
		this.shipmentStatus = shipmentStatus;
		this.remarks = remarks;
		this.cancelled = cancelled;
	}

	public ShipmentStatus getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	
	public Integer getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(Integer remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public ProductDesign getDesign() {
		return design;
	}

	public void setDesign(ProductDesign design) {
		this.design = design;
	}

	public RateType getRateType() {
		return rateType;
	}

	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getWorkDateTime() {
		return workDateTime;
	}

	public void setWorkDateTime(LocalDateTime workDateTime) {
		this.workDateTime = workDateTime;
	}

	public Worker getHandedToWorker() {
		return handedToWorker;
	}

	public void setHandedToWorker(Worker handedToWorker) {
		this.handedToWorker = handedToWorker;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isCancelled() {
		return Boolean.TRUE.equals(cancelled);
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

    	
}
