package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.FurnitureManagement.dto.ShipmentHistoryResponse;
import com.furniture.FurnitureManagement.dto.ShipmentRequest;
import com.furniture.FurnitureManagement.dto.ShowroomShipmentResponse;
import com.furniture.FurnitureManagement.entity.Shipment;
import com.furniture.FurnitureManagement.repository.ShipmentRepository;
import com.furniture.FurnitureManagement.service.ShipmentService;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    private final ShipmentRepository shipmentRepository;

    public ShipmentController(
            ShipmentService shipmentService,
            ShipmentRepository shipmentRepository) {

        this.shipmentService =
                shipmentService;

        this.shipmentRepository =
                shipmentRepository;
    }

    @PostMapping
    public Shipment createShipment(
            @RequestBody
            ShipmentRequest request) {

        return shipmentService
                .createShipment(request);
    }

    @GetMapping("/history")
    public List<ShipmentHistoryResponse>
    getShipmentHistory() {

        return shipmentService
                .getShipmentHistory();
    }
    
    @GetMapping("/showroom/{id}")
    public List<Shipment>
    getShipmentsByShowroom(
            @PathVariable Long id) {

        return shipmentService
                .getShipmentsByShowroom(id);
    }
    
    @GetMapping(
            "/showrooms/{showroomId}/shipments")
    public List<ShowroomShipmentResponse>
    getShowroomShipmentHistory(
            @PathVariable Long showroomId) {

        return shipmentService
                .getShowroomShipmentHistory(
                        showroomId);
    }

    @PutMapping("/{id}/remarks")
    public Shipment updateShipmentRemarks(
            @PathVariable Long id,
            @RequestBody ShipmentRequest request) {

        return shipmentService
                .updateRemarks(
                        id,
                        request);
    }
}
