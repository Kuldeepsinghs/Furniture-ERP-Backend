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
import com.furniture.FurnitureManagement.entity.Shipment;
import com.furniture.FurnitureManagement.entity.ShipmentItem;
import com.furniture.FurnitureManagement.entity.Showroom;
import com.furniture.FurnitureManagement.entity.WorkEntry;
import com.furniture.FurnitureManagement.repository.ShipmentItemRepository;
import com.furniture.FurnitureManagement.repository.ShipmentRepository;
import com.furniture.FurnitureManagement.repository.ShowroomRepository;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final ShipmentItemRepository shipmentItemRepository;

    private final ShowroomRepository showroomRepository;

    private final WorkEntryService workEntryService;

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            ShipmentItemRepository shipmentItemRepository,
            ShowroomRepository showroomRepository,
            WorkEntryService workEntryService) {

        this.shipmentRepository = shipmentRepository;
        this.shipmentItemRepository = shipmentItemRepository;
        this.showroomRepository = showroomRepository;
        this.workEntryService = workEntryService;
    }

    public Shipment createShipment(
            ShipmentRequest request) {

        if (request.getItems() == null
                || request.getItems().isEmpty()) {

            throw new RuntimeException(
                    "Shipment must include at least one item");
        }

        Showroom showroom =
                showroomRepository
                .findById(
                        request.getShowroomId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Showroom not found"));

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

            if (itemRequest.getWorkEntryId() == null) {

                throw new RuntimeException(
                        "Each shipment item must reference a ready stock batch");
            }

            if (itemRequest.getQuantity() == null
                    || itemRequest.getQuantity() <= 0) {

                throw new RuntimeException(
                        "Shipment item quantity must be greater than 0");
            }

            // Deducts from this exact worker's batch and updates its
            // shipment status (READY / PARTIALLY_SHIPPED / SHIPPED).
            WorkEntry batch =
                    workEntryService.shipQuantityFromWorkEntry(
                            itemRequest.getWorkEntryId(),
                            itemRequest.getQuantity());

            ShipmentItem shipmentItem =
                    new ShipmentItem();

            shipmentItem.setShipment(
                    savedShipment);

            shipmentItem.setDesign(
                    batch.getDesign());

            shipmentItem.setWorkEntry(
                    batch);

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

            List<ShipmentItemDetailResponse> itemResponses =
                    buildItemResponses(shipment);

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

            List<ShipmentItemDetailResponse> itemResponses =
                    buildItemResponses(shipment);

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

    private List<ShipmentItemDetailResponse> buildItemResponses(
            Shipment shipment) {

        List<ShipmentItem> items =
                shipmentItemRepository
                .findByShipmentId(
                        shipment.getId());

        List<ShipmentItemDetailResponse> itemResponses =
                new ArrayList<>();

        for (ShipmentItem item : items) {

            WorkEntry batch = item.getWorkEntry();

            if (batch != null) {

                itemResponses.add(

                        new ShipmentItemDetailResponse(

                                item.getDesign()
                                        .getDesignName(),

                                item.getQuantity(),

                                batch.getId(),

                                batch.getWorker().getId(),

                                batch.getWorker().getName(),

                                batch.getWorker().getRole() != null
                                        ? batch.getWorker().getRole().name()
                                        : null,

                                batch.getFinishType() != null
                                        ? batch.getFinishType().name()
                                        : null));

            } else {

                // Legacy shipment item created before batch tracking existed
                itemResponses.add(

                        new ShipmentItemDetailResponse(

                                item.getDesign()
                                        .getDesignName(),

                                item.getQuantity()));
            }
        }

        return itemResponses;
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
