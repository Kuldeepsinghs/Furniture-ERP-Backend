package com.furniture.FurnitureManagement.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.DashboardResponse;
import com.furniture.FurnitureManagement.repository.ShipmentRepository;
import com.furniture.FurnitureManagement.repository.ShowroomRepository;
import com.furniture.FurnitureManagement.repository.PaymentRepository;
import com.furniture.FurnitureManagement.repository.WorkEntryRepository;
import com.furniture.FurnitureManagement.repository.WorkerRepository;
import com.furniture.FurnitureManagement.enums.Status;

@Service
public class DashboardService {

    private final WorkerRepository workerRepository;

    private final ShowroomRepository showroomRepository;

    private final ShipmentRepository shipmentRepository;

    private final WorkEntryRepository workEntryRepository;

    private final PaymentRepository paymentRepository;

    public DashboardService(
            WorkerRepository workerRepository,
            ShowroomRepository showroomRepository,
            ShipmentRepository shipmentRepository,
            WorkEntryRepository workEntryRepository,
            PaymentRepository paymentRepository) {

        this.workerRepository =
                workerRepository;

        this.showroomRepository =
                showroomRepository;

        this.shipmentRepository =
                shipmentRepository;

        this.workEntryRepository =
                workEntryRepository;

        this.paymentRepository =
                paymentRepository;
    }

    public DashboardResponse getDashboard() {

        BigDecimal totalEarnings =
                workEntryRepository.getTotalEarned();

        BigDecimal totalPaid =
                paymentRepository.getTotalPaid();

        return new DashboardResponse(

                workerRepository.countByStatus(
                        Status.ACTIVE),

                showroomRepository.countByActiveTrue(),

                (long) workEntryRepository
                        .findDesignIdsWithAvailableBatches()
                        .size(),

                shipmentRepository.count(),

                workEntryRepository.getTotalAvailableQuantity(),

                totalEarnings,

                totalPaid,

                totalEarnings.subtract(
                        totalPaid));
    }
}
