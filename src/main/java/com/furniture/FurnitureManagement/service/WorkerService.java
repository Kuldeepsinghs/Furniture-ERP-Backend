package com.furniture.FurnitureManagement.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.WorkerLedgerResponse;
import com.furniture.FurnitureManagement.dto.WorkerRequest;
import com.furniture.FurnitureManagement.dto.WorkerStatementResponse;
import com.furniture.FurnitureManagement.dto.WorkerSummaryResponse;
import com.furniture.FurnitureManagement.entity.Payment;
import com.furniture.FurnitureManagement.entity.WorkEntry;
import com.furniture.FurnitureManagement.entity.Worker;
import com.furniture.FurnitureManagement.enums.Status;
import com.furniture.FurnitureManagement.repository.PaymentRepository;
import com.furniture.FurnitureManagement.repository.WorkEntryRepository;
import com.furniture.FurnitureManagement.repository.WorkerRepository;



@Service
public class WorkerService {

    private final WorkerRepository
            workerRepository;
    
    private final WorkEntryRepository
    		workEntryRepository;

    private final PaymentRepository
    		paymentRepository;

    public WorkerService(
            WorkerRepository workerRepository,
            WorkEntryRepository workEntryRepository,
            PaymentRepository paymentRepository) {

        this.workerRepository =
                workerRepository;

        this.workEntryRepository =
                workEntryRepository;

        this.paymentRepository =
                paymentRepository;
    }

    public Worker addWorker(
            WorkerRequest request) {

        Worker worker =
                new Worker();

        worker.setName(
                request.getName());

        worker.setPhone(
                request.getPhone());

        worker.setRole(
                request.getRole());

        worker.setJoiningDate(
                request.getJoiningDate());

        worker.setStatus(
                Status.ACTIVE);

        return workerRepository.save(
                worker);
    }

    public List<Worker> getAllWorkers() {

        return workerRepository.findByStatus(
                Status.ACTIVE);
    }
    
    public Worker updateWorker(
            Long id,
            WorkerRequest request) {

        Worker worker =
                workerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Worker not found"));

        worker.setName(
                request.getName());

        worker.setPhone(
                request.getPhone());

        worker.setRole(
                request.getRole());

        worker.setJoiningDate(
                request.getJoiningDate());

        return workerRepository.save(
                worker);
    }
    
    
    
    public Worker deactivateWorker(
            Long id) {

        Worker worker =
                workerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Worker not found"));

        worker.setStatus(
                Status.INACTIVE);

        return workerRepository.save(
                worker);
    }
    
    
    public List<Worker> searchWorkers(
            String name) {

        return workerRepository
                .findByNameContainingIgnoreCaseAndStatus(
                        name,
                        Status.ACTIVE);
    }
    
    
    public WorkerLedgerResponse
    getWorkerLedger(Long workerId) {

        Worker worker =
                workerRepository
                .findById(workerId)
                .orElseThrow();

        BigDecimal totalEarned =
                workEntryRepository
                .findByWorkerAndCancelledFalse(worker)
                .stream()
                .map(WorkEntry::getAmount)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);

        BigDecimal totalPaid =
                paymentRepository
                .findByWorkerAndCancelledFalse(worker)
                .stream()
                .map(Payment::getAmount)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);

        BigDecimal balance =
                totalEarned.subtract(
                        totalPaid);

        return new WorkerLedgerResponse(
                worker.getId(),
                worker.getName(),
                totalEarned,
                totalPaid,
                balance);
    }
    
    
    public List<WorkerSummaryResponse>
    getLedgerSummary() {

        List<Worker> workers =
                workerRepository.findByStatus(
                        Status.ACTIVE);

        return workers.stream()
                .map(worker -> {

                    BigDecimal totalEarned =
                            workEntryRepository
                            .findByWorkerAndCancelledFalse(worker)
                            .stream()
                            .map(WorkEntry::getAmount)
                            .reduce(
                                    BigDecimal.ZERO,
                                    BigDecimal::add);

                    BigDecimal totalPaid =
                            paymentRepository
                            .findByWorkerAndCancelledFalse(worker)
                            .stream()
                            .map(Payment::getAmount)
                            .reduce(
                                    BigDecimal.ZERO,
                                    BigDecimal::add);

                    BigDecimal balance =
                            totalEarned.subtract(
                                    totalPaid);

                    return new WorkerSummaryResponse(
                            worker.getId(),
                            worker.getName(),
                            totalEarned,
                            totalPaid,
                            balance);
                })
                .toList();
    }
    
    
    public WorkerStatementResponse
    getWorkerStatement(
            Long workerId) {

        Worker worker =
                workerRepository
                        .findById(workerId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Worker not found"));

        BigDecimal totalEarned =
                workEntryRepository
                        .getTotalEarnedByWorker(
                                workerId);

        BigDecimal totalPaid =
                paymentRepository
                        .getTotalPaidByWorker(
                                workerId);

        BigDecimal balance =
                totalEarned.subtract(
                        totalPaid);

        return new WorkerStatementResponse(

                worker.getId(),

                worker.getName(),

                worker.getRole().name(),

                totalEarned,

                totalPaid,

                balance);
    }
    
    
}
