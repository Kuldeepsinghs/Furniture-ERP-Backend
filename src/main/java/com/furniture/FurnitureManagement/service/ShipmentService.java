package com.furniture.FurnitureManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.ShipmentHistoryResponse;
import com.furniture.FurnitureManagement.dto.ShipmentItemDetailResponse;
import com.furniture.FurnitureManagement.dto.ShipmentItemRequest;
import com.furniture.FurnitureManagement.dto.ShipmentRequest;
import com.furniture.FurnitureManagement.dto.ShowroomShipmentResponse;
import com.furniture.FurnitureManagement.entity.ProductDesign;
import com.furniture.FurnitureManagement.entity.ReadyStock;
import com.furniture.FurnitureManagement.entity.Shipment;
import com.furniture.FurnitureManagement.entity.ShipmentItem;
import com.furniture.FurnitureManagement.entity.Showroom;
import com.furniture.FurnitureManagement.repository.ProductDesignRepository;
import com.furniture.FurnitureManagement.repository.ReadyStockRepository;
import com.furniture.FurnitureManagement.repository.ShipmentItemRepository;
import com.furniture.FurnitureManagement.repository.ShipmentRepository;
import com.furniture.FurnitureManagement.repository.ShowroomRepository;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final ShipmentItemRepository shipmentItemRepository;

    private final ShowroomRepository showroomRepository;

    private final ProductDesignRepository designRepository;

    private final ReadyStockRepository readyStockRepository;

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            ShipmentItemRepository shipmentItemRepository,
            ShowroomRepository showroomRepository,
            ProductDesignRepository designRepository,
            ReadyStockRepository readyStockRepository) {

        this.shipmentRepository = shipmentRepository;
        this.shipmentItemRepository = shipmentItemRepository;
        this.showroomRepository = showroomRepository;
        this.designRepository = designRepository;
        this.readyStockRepository = readyStockRepository;
    }

    public Shipment createShipment(
            ShipmentRequest request) {

        Showroom showroom =
                showroomRepository
                .findById(
                        request.getShowroomId())
                .orElseThrow();

        Shipment shipment =
                new Shipment();

        shipment.setShowroom(showroom);

        shipment.setShipmentDateTime(
                LocalDateTime.now());

        shipment.setRemarks(
                request.getRemarks());

        Shipment savedShipment =
                shipmentRepository.save(
                        shipment);

        for (ShipmentItemRequest itemRequest
                : request.getItems()) {

            ProductDesign design =
                    designRepository
                    .findById(
                            itemRequest.getDesignId())
                    .orElseThrow();

            ReadyStock stock =
                    readyStockRepository
                    .findByDesign(design)
                    .orElseThrow();

            if (stock.getAvailableQuantity()
                    < itemRequest.getQuantity()) {

                throw new RuntimeException(
                        "Insufficient stock for "
                                + design.getDesignName());
            }

            stock.setAvailableQuantity(
                    stock.getAvailableQuantity()
                    - itemRequest.getQuantity());

            readyStockRepository.save(
                    stock);

            ShipmentItem shipmentItem =
                    new ShipmentItem();

            shipmentItem.setShipment(
                    savedShipment);

            shipmentItem.setDesign(
                    design);

            shipmentItem.setQuantity(
                    itemRequest.getQuantity());

            shipmentItemRepository.save(
                    shipmentItem);
        }

        return savedShipment;
    }
    
    public List<Shipment>
    getShipmentsByShowroom(
            Long showroomId) {

        Showroom showroom =
                showroomRepository
                .findById(showroomId)
                .orElseThrow();

        return shipmentRepository
                .findByShowroom(showroom);
    }
    
    public List<ShowroomShipmentResponse>
    getShowroomShipmentHistory(
            Long showroomId) {

        List<Shipment> shipments =
                shipmentRepository
                .findByShowroomIdOrderByShipmentDateTimeDesc(
                        showroomId);

        List<ShowroomShipmentResponse> response =
                new ArrayList<>();

        for (Shipment shipment : shipments) {

            List<ShipmentItem> items =
                    shipmentItemRepository
                    .findByShipmentId(
                            shipment.getId());

            List<ShipmentItemDetailResponse>
                    itemResponses =
                    new ArrayList<>();

            for (ShipmentItem item : items) {

                itemResponses.add(

                        new ShipmentItemDetailResponse(

                                item.getDesign()
                                        .getDesignName(),

                                item.getQuantity()));
            }
            

            response.add(

                    new ShowroomShipmentResponse(

                            shipment.getId(),

                            shipment.getShipmentDateTime(),

                            shipment.getRemarks(),

                            itemResponses));
        }

        return response;
    }
    
    public List<ShipmentHistoryResponse>
    getShipmentHistory() {

        List<Shipment> shipments =
                shipmentRepository.findAll();

        List<ShipmentHistoryResponse> response =
                new ArrayList<>();

        for (Shipment shipment : shipments) {

            List<ShipmentItem> items =
                    shipmentItemRepository
                    .findByShipmentId(
                            shipment.getId());

            List<ShipmentItemDetailResponse>
                    itemResponses =
                    new ArrayList<>();

            for (ShipmentItem item : items) {

                itemResponses.add(

                        new ShipmentItemDetailResponse(

                                item.getDesign()
                                        .getDesignName(),

                                item.getQuantity()));
            }

            response.add(

                    new ShipmentHistoryResponse(

                            shipment.getId(),

                            shipment.getShowroom()
                                    .getName(),

                            shipment.getShipmentDateTime(),

                            shipment.getRemarks(),

                            itemResponses));
        }

        return response;
    }

    public Shipment updateRemarks(
            Long id,
            ShipmentRequest request) {

        Shipment shipment =
                shipmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shipment not found"));

        shipment.setRemarks(
                request.getRemarks());

        return shipmentRepository.save(
                shipment);
    }
    
    
}
