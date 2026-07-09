package com.furniture.FurnitureManagement.dto;

public class WorkerClearDataResponse {

    private Long workerId;

    private String workerName;

    private int deletedWorkEntries;

    private int deletedPayments;

    public WorkerClearDataResponse() {
    }

    public WorkerClearDataResponse(
            Long workerId,
            String workerName,
            int deletedWorkEntries,
            int deletedPayments) {

        this.workerId = workerId;
        this.workerName = workerName;
        this.deletedWorkEntries = deletedWorkEntries;
        this.deletedPayments = deletedPayments;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public int getDeletedWorkEntries() {
        return deletedWorkEntries;
    }

    public void setDeletedWorkEntries(int deletedWorkEntries) {
        this.deletedWorkEntries = deletedWorkEntries;
    }

    public int getDeletedPayments() {
        return deletedPayments;
    }

    public void setDeletedPayments(int deletedPayments) {
        this.deletedPayments = deletedPayments;
    }
}