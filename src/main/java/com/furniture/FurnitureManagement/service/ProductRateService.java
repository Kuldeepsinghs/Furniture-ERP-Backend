package com.furniture.FurnitureManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.ProductRateRequest;
import com.furniture.FurnitureManagement.entity.ProductDesign;
import com.furniture.FurnitureManagement.entity.ProductRate;
import com.furniture.FurnitureManagement.entity.RateType;
import com.furniture.FurnitureManagement.repository.ProductDesignRepository;
import com.furniture.FurnitureManagement.repository.ProductRateRepository;
import com.furniture.FurnitureManagement.repository.RateTypeRepository;

@Service
public class ProductRateService {

    private final ProductRateRepository
            rateRepository;

    private final ProductDesignRepository
            designRepository;

    private final RateTypeRepository
            rateTypeRepository;

    public ProductRateService(
            ProductRateRepository rateRepository,
            ProductDesignRepository designRepository,
            RateTypeRepository rateTypeRepository) {

        this.rateRepository =
                rateRepository;

        this.designRepository =
                designRepository;

        this.rateTypeRepository =
                rateTypeRepository;
    }

    public ProductRate addRate(
            ProductRateRequest request) {

        ProductDesign design =
                designRepository
                .findById(
                        request.getDesignId())
                .orElseThrow();

        RateType rateType =
                rateTypeRepository
                .findById(
                        request.getRateTypeId())
                .orElseThrow();

        ProductRate rate =
                new ProductRate();

        rate.setDesign(
                design);

        rate.setRateType(
                rateType);

        updateRateValues(
                rate,
                request);

        return rateRepository.save(
                rate);
    }

    public List<ProductRate> getAllRates() {

        return rateRepository.findByActiveTrue();
    }
    
    public ProductRate updateRate(
            Long id,
            ProductRateRequest request) {

        ProductRate rate =
                rateRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Rate not found"));

        updateRateValues(
                rate,
                request);

        return rateRepository
                .save(rate);
    }

    public ProductRate deleteRate(
            Long id) {

        ProductRate rate =
                rateRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Rate not found"));

        rate.setActive(false);

        return rateRepository.save(
                rate);
    }

    private void updateRateValues(
            ProductRate rate,
            ProductRateRequest request) {

        rate.setAmount(
                request.getAmount() != null
                ? request.getAmount()
                : request.getCarpenterRate() != null
                ? request.getCarpenterRate()
                : request.getPolisherRate());

        rate.setCarpenterRate(
                request.getCarpenterRate());

        rate.setPolisherRate(
                request.getPolisherRate());
    }
}
