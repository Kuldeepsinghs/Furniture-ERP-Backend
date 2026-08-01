package com.furniture.FurnitureManagement.dto;

public class ShipmentItemDetailResponse {

    private String designName;

    private Integer quantity;

    private Long workEntryId;

    private Long workerId;

    private String workerName;

    private String workerRole;

    private String finishType;

    public ShipmentItemDetailResponse(
            String designName,
            Integer quantity) {

        this.designName = designName;
        this.quantity = quantity;
    }

    public ShipmentItemDetailResponse(
            String designName,
            Integer quantity,
            Long workEntryId,
            Long workerId,
            String workerName,
            String workerRole,
            String finishType) {

        this.designName = designName;
        this.quantity = quantity;
        this.workEntryId = workEntryId;
        this.workerId = workerId;
        this.workerName = workerName;
        this.workerRole = workerRole;
        this.finishType = finishType;
    }

    public String getDesignName() {
        return designName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getWorkEntryId() {
        return workEntryId;
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
}