package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.furniture.FurnitureManagement.dto.ProductRateRequest;
import com.furniture.FurnitureManagement.entity.ProductRate;
import com.furniture.FurnitureManagement.service.ProductRateService;

@RestController
@RequestMapping("/product-rates")
public class ProductRateController {

    private final ProductRateService
            service;

    public ProductRateController(
            ProductRateService service) {

        this.service = service;
    }

    @PostMapping
    public ProductRate addRate(
            @RequestBody
            ProductRateRequest request) {

        return service.addRate(
                request);
    }

    @GetMapping
    public List<ProductRate> getAllRates() {

        return service.getAllRates();
    }
    
    @PutMapping("/{id}")
    public ProductRate updateRate(
            @PathVariable Long id,
            @RequestBody ProductRateRequest request) {

        return service.updateRate(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    public ProductRate deleteRate(
            @PathVariable Long id) {

        return service.deleteRate(id);
    }
    
    @GetMapping("/test")
    public String test() {
        return "PRODUCT RATE CONTROLLER WORKING";
    }
}
