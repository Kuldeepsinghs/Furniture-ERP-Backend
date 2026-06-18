package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.furniture.FurnitureManagement.dto.RateTypeRequest;
import com.furniture.FurnitureManagement.entity.RateType;
import com.furniture.FurnitureManagement.service.RateTypeService;

@RestController
@RequestMapping("/rate-types")
public class RateTypeController {

    private final RateTypeService
            service;

    public RateTypeController(
            RateTypeService service) {

        this.service = service;
    }

    @PostMapping
    public RateType addRateType(
            @RequestBody
            RateTypeRequest request) {

        return service.addRateType(
                request);
    }

    @GetMapping
    public List<RateType>
    getAllRateTypes() {

        return service.getAllRateTypes();
    }

    @PutMapping("/{id}")
    public RateType updateRateType(
            @PathVariable Long id,
            @RequestBody RateTypeRequest request) {

        return service.updateRateType(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    public RateType deleteRateType(
            @PathVariable Long id) {

        return service.deleteRateType(id);
    }
}
