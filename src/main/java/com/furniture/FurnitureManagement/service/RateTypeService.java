package com.furniture.FurnitureManagement.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.RateTypeRequest;
import com.furniture.FurnitureManagement.entity.RateType;
import com.furniture.FurnitureManagement.repository.RateTypeRepository;

@Service
public class RateTypeService {

    private final RateTypeRepository
            repository;

    public RateTypeService(
            RateTypeRepository repository) {

        this.repository = repository;
    }

    public RateType addRateType(
            RateTypeRequest request) {

        RateType rateType =
                new RateType();

        rateType.setName(
                request.getName());

        rateType.setDefaultCarpenterRate(
                request.getDefaultCarpenterRate());

        rateType.setDefaultPolisherRate(
                request.getDefaultPolisherRate());

        rateType.setWalnutExtra(
                request.getWalnutExtra() != null
                ? request.getWalnutExtra()
                : BigDecimal.ZERO);

        return repository.save(
                rateType);
    }

    public List<RateType>
    getAllRateTypes() {

        return repository.findByActiveTrue();
    }

    public RateType updateRateType(
            Long id,
            RateTypeRequest request) {

        RateType rateType =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Rate Type not found"));

        rateType.setName(
                request.getName());

        rateType.setDefaultCarpenterRate(
                request.getDefaultCarpenterRate());

        rateType.setDefaultPolisherRate(
                request.getDefaultPolisherRate());

        rateType.setWalnutExtra(
                request.getWalnutExtra() != null
                ? request.getWalnutExtra()
                : BigDecimal.ZERO);

        return repository.save(
                rateType);
    }

    public RateType deleteRateType(
            Long id) {

        RateType rateType =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Rate Type not found"));

        rateType.setActive(false);

        return repository.save(
                rateType);
    }
}
