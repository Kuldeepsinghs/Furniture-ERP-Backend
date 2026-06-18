package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.furniture.FurnitureManagement.dto.ProductDesignRequest;
import com.furniture.FurnitureManagement.entity.ProductDesign;
import com.furniture.FurnitureManagement.service.ProductDesignService;

@RestController
@RequestMapping("/designs")
public class ProductDesignController {

    private final ProductDesignService
            service;

    public ProductDesignController(
            ProductDesignService service) {

        this.service = service;
    }

    @PostMapping
    public ProductDesign addDesign(
            @RequestBody
            ProductDesignRequest request) {

        return service.addDesign(
                request);
    }

    @GetMapping
    public List<ProductDesign>
    getAllDesigns() {

        return service.getAllDesigns();
    }

    @PutMapping("/{id}")
    public ProductDesign updateDesign(
            @PathVariable Long id,
            @RequestBody ProductDesignRequest request) {

        return service.updateDesign(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    public ProductDesign deleteDesign(
            @PathVariable Long id) {

        return service.deleteDesign(id);
    }
}
