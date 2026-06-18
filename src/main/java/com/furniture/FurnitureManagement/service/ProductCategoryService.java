package com.furniture.FurnitureManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.ProductCategoryRequest;
import com.furniture.FurnitureManagement.entity.ProductCategory;
import com.furniture.FurnitureManagement.repository.ProductCategoryRepository;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository
            repository;

    public ProductCategoryService(
            ProductCategoryRepository repository) {

        this.repository = repository;
    }

    public ProductCategory addCategory(
            ProductCategoryRequest request) {

        ProductCategory category =
                new ProductCategory();

        category.setName(
                request.getName());

        return repository.save(
                category);
    }

    public List<ProductCategory>
    getAllCategories() {

        return repository.findByActiveTrue();
    }

    public ProductCategory updateCategory(
            Long id,
            ProductCategoryRequest request) {

        ProductCategory category =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"));

        category.setName(
                request.getName());

        return repository.save(
                category);
    }

    public ProductCategory deleteCategory(
            Long id) {

        ProductCategory category =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"));

        category.setActive(false);

        return repository.save(
                category);
    }
}
