package com.furniture.FurnitureManagement.controller;

import org.springframework.web.bind.annotation.*;

import com.furniture.FurnitureManagement.dto.DashboardResponse;
import com.furniture.FurnitureManagement.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService
            dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService =
                dashboardService;
    }

    @GetMapping
    public DashboardResponse
    getDashboard() {

        return dashboardService
                .getDashboard();
    }
}