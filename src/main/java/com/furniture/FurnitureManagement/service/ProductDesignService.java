package com.furniture.FurnitureManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.ProductDesignRequest;
import com.furniture.FurnitureManagement.entity.ProductCategory;
import com.furniture.FurnitureManagement.entity.ProductDesign;
import com.furniture.FurnitureManagement.repository.ProductCategoryRepository;
import com.furniture.FurnitureManagement.repository.ProductDesignRepository;

@Service
public class ProductDesignService {

    private final ProductDesignRepository
            designRepository;

    private final ProductCategoryRepository
            categoryRepository;

    public ProductDesignService(
            ProductDesignRepository designRepository,
            ProductCategoryRepository categoryRepository) {

        this.designRepository =
                designRepository;

        this.categoryRepository =
                categoryRepository;
    }

    public ProductDesign addDesign(
            ProductDesignRequest request) {

        ProductCategory category =
                categoryRepository
                .findById(
                        request.getCategoryId())
                .orElseThrow();

        ProductDesign design =
                new ProductDesign();

        design.setDesignName(
                request.getDesignName());

        design.setCategory(
                category);

        return designRepository.save(
                design);
    }

    public List<ProductDesign>
    getAllDesigns() {

        return designRepository.findByActiveTrue();
    }

    public ProductDesign updateDesign(
            Long id,
            ProductDesignRequest request) {

        ProductDesign design =
                designRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Design not found"));

        ProductCategory category =
                categoryRepository
                .findById(
                        request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"));

        design.setDesignName(
                request.getDesignName());

        design.setCategory(
                category);

        return designRepository.save(
                design);
    }

    public ProductDesign deleteDesign(
            Long id) {

        ProductDesign design =
                designRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Design not found"));

        design.setActive(false);

        return designRepository.save(
                design);
    }
}
