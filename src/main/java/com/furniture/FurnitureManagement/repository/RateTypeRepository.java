package com.furniture.FurnitureManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.furniture.FurnitureManagement.entity.RateType;

public interface RateTypeRepository
        extends JpaRepository<RateType, Long> {

    List<RateType> findByActiveTrue();
}
