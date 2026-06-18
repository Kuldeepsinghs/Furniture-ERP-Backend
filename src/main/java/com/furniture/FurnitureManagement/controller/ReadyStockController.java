package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.furniture.FurnitureManagement.entity.ReadyStock;
import com.furniture.FurnitureManagement.service.ReadyStockService;

@RestController
@RequestMapping("/ready-stock")
public class ReadyStockController {

    private final ReadyStockService
            readyStockService;

    public ReadyStockController(
            ReadyStockService readyStockService) {

        this.readyStockService =
                readyStockService;
    }

    @GetMapping
    public List<ReadyStock>
    getAllReadyStock() {

        return readyStockService
                .getAllReadyStock();
    }
    
    
}