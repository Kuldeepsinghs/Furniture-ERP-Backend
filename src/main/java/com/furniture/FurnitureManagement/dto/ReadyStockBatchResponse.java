package com.furniture.FurnitureManagement.dto;

import java.time.LocalDateTime;

public class ReadyStockBatchResponse {

    // This is the WorkEntry id - used when creating a shipment to reference
    // exactly which batch/worker the shipped quantity should come from.
    private Long workEntryId;

    private Long designId;

    private String designName;

    private String categoryName;

    private Long workerId;

    private String workerName;

    private String workerRole;

    private String finishType;

    private Integer originalQuantity;

    private Integer remainingQuantity;

    private String shipmentStatus;

    private LocalDateTime workDateTime;

    public ReadyStockBatchResponse(
            Long workEntryId,
            Long designId,
            String designName,
            String categoryName,
            Long workerId,
            String workerName,
            String workerRole,
            String finishType,
            Integer originalQuantity,
            Integer remainingQuantity,
            String shipmentStatus,
            LocalDateTime workDateTime) {

        this.workEntryId = workEntryId;
        this.designId = designId;
        this.designName = designName;
        this.categoryName = categoryName;
        this.workerId = workerId;
        this.workerName = workerName;
        this.workerRole = workerRole;
        this.finishType = finishType;
        this.originalQuantity = originalQuantity;
        this.remainingQuantity = remainingQuantity;
        this.shipmentStatus = shipmentStatus;
        this.workDateTime = workDateTime;
    }

    public Long getWorkEntryId() {
        return workEntryId;
    }

    public Long getDesignId() {
        return designId;
    }

    public String getDesignName() {
        return designName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerRole() {
        return workerRole;
    }

    public String getFinishType() {
        return finishType;
    }

    public Integer getOriginalQuantity() {
        return originalQuantity;
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public LocalDateTime getWorkDateTime() {
        return workDateTime;
    }
}
