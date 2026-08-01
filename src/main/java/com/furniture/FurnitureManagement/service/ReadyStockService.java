package com.furniture.FurnitureManagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.ReadyStockBatchResponse;
import com.furniture.FurnitureManagement.entity.WorkEntry;
import com.furniture.FurnitureManagement.repository.WorkEntryRepository;

/**
 * Ready Stock is now sourced directly from WorkEntry batches instead of the
 * old design-only ReadyStock table, so every unit in stock is traceable
 * back to the worker who made it.
 */
@Service
public class ReadyStockService {

    private final WorkEntryRepository workEntryRepository;

    public ReadyStockService(
            WorkEntryRepository workEntryRepository) {

        this.workEntryRepository = workEntryRepository;
    }

    public List<ReadyStockBatchResponse>
    getAllReadyStock() {

        List<WorkEntry> batches =
                workEntryRepository
                .findAllAvailableBatches();

        return batches.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ReadyStockBatchResponse toResponse(
            WorkEntry entry) {

        return new ReadyStockBatchResponse(

                entry.getId(),

                entry.getDesign().getId(),

                entry.getDesign().getDesignName(),

                entry.getDesign().getCategory() != null
                        ? entry.getDesign().getCategory().getName()
                        : null,

                entry.getWorker().getId(),

                entry.getWorker().getName(),

                entry.getWorker().getRole() != null
                        ? entry.getWorker().getRole().name()
                        : null,

                entry.getFinishType() != null
                        ? entry.getFinishType().name()
                        : null,

                entry.getQuantity(),

                entry.getRemainingQuantity(),

                entry.getShipmentStatus() != null
                        ? entry.getShipmentStatus().name()
                        : null,

                entry.getWorkDateTime());
    }
}
