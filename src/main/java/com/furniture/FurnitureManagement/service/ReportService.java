package com.furniture.FurnitureManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.ProductionReportResponse;
import com.furniture.FurnitureManagement.repository.WorkEntryRepository;

@Service
public class ReportService {

    private final WorkEntryRepository
            workEntryRepository;

    public ReportService(
            WorkEntryRepository workEntryRepository) {

        this.workEntryRepository =
                workEntryRepository;
    }

    public List<ProductionReportResponse>
    getProductionReport() {

        return workEntryRepository
                .getProductionReport();
    }
}