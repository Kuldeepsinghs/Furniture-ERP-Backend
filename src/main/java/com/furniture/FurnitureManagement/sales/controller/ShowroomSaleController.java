package com.furniture.FurnitureManagement.sales.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.FurnitureManagement.sales.dto.SalesDashboardResponse;
import com.furniture.FurnitureManagement.sales.dto.SalesReportResponse;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSalePageResponse;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleRequest;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleResponse;
import com.furniture.FurnitureManagement.sales.service.ShowroomSaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sales")
public class ShowroomSaleController {

    private final ShowroomSaleService service;

    public ShowroomSaleController(
            ShowroomSaleService service) {

        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ShowroomSaleResponse> createSale(
            @Valid
            @RequestBody
            ShowroomSaleRequest request) {

        return ResponseEntity.ok(
                service.createSale(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowroomSaleResponse> updateSale(
            @PathVariable Long id,
            @Valid
            @RequestBody
            ShowroomSaleRequest request) {

        return ResponseEntity.ok(
                service.updateSale(
                        id,
                        request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ShowroomSaleResponse> deleteSale(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.deleteSale(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowroomSaleResponse> getSaleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getSaleById(id));
    }

    @GetMapping
    public ResponseEntity<ShowroomSalePageResponse> getAllSales(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @RequestParam(defaultValue = "saleDateTime")
            String sortBy,
            @RequestParam(defaultValue = "desc")
            String sortDir,
            @RequestParam(required = false)
            String search) {

        return ResponseEntity.ok(
                service.getAllSales(
                        page,
                        size,
                        sortBy,
                        sortDir,
                        search));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<SalesDashboardResponse> getDashboard() {

        return ResponseEntity.ok(
                service.getDashboard());
    }

    @GetMapping("/reports/today")
    public ResponseEntity<SalesReportResponse> getTodayReport() {

        return ResponseEntity.ok(
                service.getTodayReport());
    }

    @GetMapping("/reports/weekly")
    public ResponseEntity<SalesReportResponse> getWeeklyReport() {

        return ResponseEntity.ok(
                service.getWeeklyReport());
    }

    @GetMapping("/reports/monthly")
    public ResponseEntity<SalesReportResponse> getMonthlyReport() {

        return ResponseEntity.ok(
                service.getMonthlyReport());
    }

    @GetMapping("/reports/yearly")
    public ResponseEntity<SalesReportResponse> getYearlyReport() {

        return ResponseEntity.ok(
                service.getYearlyReport());
    }

    @GetMapping("/reports/custom")
    public ResponseEntity<SalesReportResponse> getCustomReport(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return ResponseEntity.ok(
                service.getCustomReport(
                        startDate,
                        endDate));
    }
}
