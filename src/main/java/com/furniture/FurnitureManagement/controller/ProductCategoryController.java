package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.furniture.FurnitureManagement.dto.ProductCategoryRequest;
import com.furniture.FurnitureManagement.entity.ProductCategory;
import com.furniture.FurnitureManagement.service.ProductCategoryService;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

    private final ProductCategoryService
            service;

    public ProductCategoryController(
            ProductCategoryService service) {

        this.service = service;
    }

    @PostMapping
    public ProductCategory addCategory(
            @RequestBody
            ProductCategoryRequest request) {

        return service.addCategory(
                request);
    }

    @GetMapping
    public List<ProductCategory>
    getAllCategories() {

        return service.getAllCategories();
    }

    @PutMapping("/{id}")
    public ProductCategory updateCategory(
            @PathVariable Long id,
            @RequestBody ProductCategoryRequest request) {

        return service.updateCategory(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    public ProductCategory deleteCategory(
            @PathVariable Long id) {

        return service.deleteCategory(id);
    }
}
