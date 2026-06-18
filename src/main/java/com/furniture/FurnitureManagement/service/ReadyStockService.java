package com.furniture.FurnitureManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.entity.ReadyStock;
import com.furniture.FurnitureManagement.repository.ReadyStockRepository;

@Service
public class ReadyStockService {

    private final ReadyStockRepository
            readyStockRepository;

    public ReadyStockService(
            ReadyStockRepository readyStockRepository) {

        this.readyStockRepository =
                readyStockRepository;
    }

    public List<ReadyStock>
    getAllReadyStock() {

        return readyStockRepository
                .findAll();
    }
}