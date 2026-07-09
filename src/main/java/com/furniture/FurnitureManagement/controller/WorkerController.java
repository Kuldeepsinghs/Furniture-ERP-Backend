package com.furniture.FurnitureManagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.furniture.FurnitureManagement.dto.WorkerClearDataResponse;
import com.furniture.FurnitureManagement.dto.WorkerLedgerResponse;
import com.furniture.FurnitureManagement.dto.WorkerRequest;
import com.furniture.FurnitureManagement.dto.WorkerStatementResponse;
import com.furniture.FurnitureManagement.dto.WorkerSummaryResponse;
import com.furniture.FurnitureManagement.entity.Worker;
import com.furniture.FurnitureManagement.service.WorkerService;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    private final WorkerService
            workerService;

    public WorkerController(
            WorkerService workerService) {

        this.workerService =
                workerService;
    }

    @PostMapping
    public Worker addWorker(
            @RequestBody
            WorkerRequest request) {

        return workerService
                .addWorker(request);
    }
    

    @GetMapping
    public List<Worker> getAllWorkers() {

        return workerService
                .getAllWorkers();
    }
    
    @PutMapping("/{id}")
    public Worker updateWorker(
            @PathVariable Long id,
            @RequestBody WorkerRequest request) {

        return workerService
                .updateWorker(
                        id,
                        request);
    }
    
    @PatchMapping("/{id}/deactivate")
    public Worker deactivateWorker(
            @PathVariable Long id) {

        return workerService
                .deactivateWorker(id);
    }

    @DeleteMapping("/{id}")
    public Worker deleteWorker(
            @PathVariable Long id) {

        return workerService
                .deactivateWorker(id);
    }


    @DeleteMapping("/{id}/clear-data")
    public WorkerClearDataResponse
    clearWorkerData(
            @PathVariable Long id) {

        return workerService
                .clearWorkerData(id);
    }
    
    
    @GetMapping("/search")
    public List<Worker> searchWorkers(
            @RequestParam String name) {

        return workerService
                .searchWorkers(name);
    }
    
    @GetMapping("/{id}/ledger")
    public WorkerLedgerResponse
    getLedger(
            @PathVariable Long id) {

        return workerService
                .getWorkerLedger(id);
    }
    
    @GetMapping("/ledger-summary")
    public List<WorkerSummaryResponse>
    getLedgerSummary() {

        return workerService
                .getLedgerSummary();
    }
    
    @GetMapping("/{id}/statement")
    public WorkerStatementResponse
    getWorkerStatement(
            @PathVariable Long id) {

        return workerService
                .getWorkerStatement(id);
    }
    
}