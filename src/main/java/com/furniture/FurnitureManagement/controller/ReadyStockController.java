package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.furniture.FurnitureManagement.dto.ReadyStockBatchResponse;
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

    // Returns one row per worker's batch (design + worker + remaining qty),
    // so the UI can show exactly who made what is currently in stock.
    @GetMapping
    public List<ReadyStockBatchResponse>
    getAllReadyStock() {

        return readyStockService
                .getAllReadyStock();
    }
    
    
}