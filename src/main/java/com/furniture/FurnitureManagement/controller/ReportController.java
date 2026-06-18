package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.furniture.FurnitureManagement.dto.ProductionReportResponse;
import com.furniture.FurnitureManagement.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(
            ReportService reportService) {

        this.reportService =
                reportService;
    }

    @GetMapping("/production")
    public List<ProductionReportResponse>
    getProductionReport() {

        return reportService
                .getProductionReport();
    }
}