package com.furniture.FurnitureManagement.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.WorkEntryRequest;
import com.furniture.FurnitureManagement.entity.ProductDesign;
import com.furniture.FurnitureManagement.entity.ProductRate;
import com.furniture.FurnitureManagement.entity.RateType;
import com.furniture.FurnitureManagement.entity.ReadyStock;
import com.furniture.FurnitureManagement.entity.WorkEntry;
import com.furniture.FurnitureManagement.entity.Worker;
import com.furniture.FurnitureManagement.enums.FinishType;
import com.furniture.FurnitureManagement.enums.ShipmentStatus;
import com.furniture.FurnitureManagement.enums.WorkerRole;
import com.furniture.FurnitureManagement.repository.ProductDesignRepository;
import com.furniture.FurnitureManagement.repository.ProductRateRepository;
import com.furniture.FurnitureManagement.repository.RateTypeRepository;
import com.furniture.FurnitureManagement.repository.ReadyStockRepository;
import com.furniture.FurnitureManagement.repository.WorkEntryRepository;
import com.furniture.FurnitureManagement.repository.WorkerRepository;

@Service
public class WorkEntryService {

    private final WorkEntryRepository workEntryRepository;

    private final WorkerRepository workerRepository;

    private final ProductDesignRepository designRepository;

    private final RateTypeRepository rateTypeRepository;

    private final ProductRateRepository productRateRepository;

    private final ReadyStockRepository readyStockRepository;

    public WorkEntryService(
            WorkEntryRepository workEntryRepository,
            WorkerRepository workerRepository,
            ProductDesignRepository designRepository,
            RateTypeRepository rateTypeRepository,
            ProductRateRepository productRateRepository,
            ReadyStockRepository readyStockRepository) {

        this.workEntryRepository = workEntryRepository;
        this.workerRepository = workerRepository;
        this.designRepository = designRepository;
        this.rateTypeRepository = rateTypeRepository;
        this.productRateRepository = productRateRepository;
        this.readyStockRepository = readyStockRepository;
    }

    public WorkEntry addWorkEntry(
            WorkEntryRequest request) {

        Worker worker =
                workerRepository
                .findById(request.getWorkerId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Worker not found"));

        ProductDesign design =
                designRepository
                .findById(request.getDesignId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Design not found"));

        RateType rateType =
                rateTypeRepository
                .findById(request.getRateTypeId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Rate Type not found"));

        ProductRate productRate =
                productRateRepository
                .findByDesignAndRateTypeAndActiveTrue(
                        design,
                        rateType)
                .orElse(null);

        WorkEntry entry =
                new WorkEntry();

        entry.setWorker(worker);

        entry.setDesign(design);

        entry.setRateType(rateType);

        Integer quantity =
                request.getQuantity() != null
                ? request.getQuantity()
                : 1;

        if (quantity <= 0) {

            throw new RuntimeException(
                    "Quantity must be greater than 0");
        }

        entry.setQuantity(quantity);

        FinishType finishType =
                request.getFinishType() != null
                ? request.getFinishType()
                : FinishType.NORMAL;

        entry.setFinishType(finishType);

        BigDecimal unitRate =
                calculateUnitRate(
                        worker,
                        rateType,
                        productRate,
                        finishType);

        entry.setUnitRate(
                unitRate);

        entry.setAmount(
                unitRate
                .multiply(
                        BigDecimal.valueOf(
                                quantity)));

        if (worker.getRole()
                == WorkerRole.POLISHER) {

            entry.setShipmentStatus(
                    ShipmentStatus.READY);
        }

        entry.setRemarks(
                request.getRemarks());

        entry.setWorkDateTime(
                request.getWorkDateTime() != null
                        ? request.getWorkDateTime()
                        : LocalDateTime.now());

        if (request.getHandedToWorkerId()
                != null) {

            Worker handedToWorker =
                    workerRepository
                    .findById(
                            request.getHandedToWorkerId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Handed To Worker not found"));

            entry.setHandedToWorker(
                    handedToWorker);
        }

        WorkEntry savedEntry =
                workEntryRepository
                .save(entry);

        if (worker.getRole()
                == WorkerRole.POLISHER) {

            adjustReadyStock(
                    design,
                    quantity);
        }

        return savedEntry;
    }

    public List<WorkEntry> getAllEntries() {

        return workEntryRepository
                .findByCancelledFalse();
    }

    public List<WorkEntry> getReadyItems() {

        return workEntryRepository
                .findByShipmentStatusAndCancelledFalse(
                        ShipmentStatus.READY);
    }
    
    public WorkEntry updateWorkEntry(
            Long id,
            WorkEntryRequest request) {

        WorkEntry entry =
                workEntryRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Work Entry not found"));

        if (entry.isCancelled()) {

            throw new RuntimeException(
                    "Unable to Update");
        }

        int oldQuantity =
                entry.getQuantity();

        int newQuantity =
                request.getQuantity() != null
                ? request.getQuantity()
                : entry.getQuantity();

        if (newQuantity <= 0) {

            throw new RuntimeException(
                    "Quantity must be greater than 0");
        }

        entry.setQuantity(
                newQuantity);

        RateType rateType =
                entry.getRateType();

        if (request.getRateTypeId()
                != null) {

            rateType =
                    rateTypeRepository
                    .findById(
                            request.getRateTypeId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Rate Type not found"));

            entry.setRateType(
                    rateType);
        }

        FinishType finishType =
                request.getFinishType() != null
                ? request.getFinishType()
                : entry.getFinishType() != null
                ? entry.getFinishType()
                : FinishType.NORMAL;

        entry.setFinishType(
                finishType);

        ProductRate productRate =
                productRateRepository
                .findByDesignAndRateTypeAndActiveTrue(
                        entry.getDesign(),
                        rateType)
                .orElse(null);

        BigDecimal unitRate =
                calculateUnitRate(
                        entry.getWorker(),
                        rateType,
                        productRate,
                        finishType);

        entry.setUnitRate(
                unitRate);

        entry.setAmount(
                unitRate
                .multiply(
                        BigDecimal.valueOf(
                                newQuantity)));

        entry.setRemarks(
                request.getRemarks());

        if (request.getWorkDateTime()
                != null) {

            entry.setWorkDateTime(
                    request.getWorkDateTime());
        }

        WorkEntry savedEntry =
                workEntryRepository
                .save(entry);

        if (entry.getWorker().getRole()
                == WorkerRole.POLISHER) {

            adjustReadyStock(
                    entry.getDesign(),
                    newQuantity - oldQuantity);
        }

        return savedEntry;
    }

    public WorkEntry cancelWorkEntry(
            Long id) {

        WorkEntry entry =
                workEntryRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Work Entry not found"));

        if (entry.isCancelled()) {

            return entry;
        }

        entry.setCancelled(true);

        if (entry.getWorker().getRole()
                == WorkerRole.POLISHER) {

            adjustReadyStock(
                    entry.getDesign(),
                    -entry.getQuantity());
        }

        return workEntryRepository
                .save(entry);
    }

    private ReadyStock adjustReadyStock(
            ProductDesign design,
            int quantityDelta) {

        ReadyStock readyStock =
                readyStockRepository
                .findByDesign(design)
                .orElseGet(() -> {

                    ReadyStock stock =
                            new ReadyStock();

                    stock.setDesign(
                            design);

                    stock.setAvailableQuantity(
                            0);

                    return stock;
                });

        int newQuantity =
                readyStock.getAvailableQuantity()
                + quantityDelta;

        readyStock.setAvailableQuantity(
                Math.max(
                        newQuantity,
                        0));

        readyStock.setLastUpdated(
                LocalDateTime.now());

        return readyStockRepository
                .save(readyStock);
    }

    private BigDecimal calculateUnitRate(
            Worker worker,
            RateType rateType,
            ProductRate productRate,
            FinishType finishType) {

        BigDecimal rate =
                getBaseRate(
                        worker,
                        rateType,
                        productRate);

        if (worker.getRole() == WorkerRole.POLISHER
                && finishType == FinishType.WALNUT) {

            rate =
                    rate.add(
                            rateType.getWalnutExtra() != null
                            ? rateType.getWalnutExtra()
                            : BigDecimal.ZERO);
        }

        return rate;
    }

    private BigDecimal getBaseRate(
            Worker worker,
            RateType rateType,
            ProductRate productRate) {

        BigDecimal rate = null;

        if (productRate != null) {

            boolean hasRoleSpecificRate =
                    productRate.getCarpenterRate() != null
                    || productRate.getPolisherRate() != null;

            if (worker.getRole() == WorkerRole.CARPENTER) {

                rate =
                        hasRoleSpecificRate
                        ? productRate.getCarpenterRate()
                        : productRate.getAmount();
            }

            if (worker.getRole() == WorkerRole.POLISHER) {

                rate =
                        hasRoleSpecificRate
                        ? productRate.getPolisherRate()
                        : productRate.getAmount();
            }
        }

        if (rate == null) {

            if (productRate == null
                    && worker.getRole() == WorkerRole.CARPENTER) {

                rate =
                        rateType.getDefaultCarpenterRate();
            }

            if (productRate == null
                    && worker.getRole() == WorkerRole.POLISHER) {

                rate =
                        rateType.getDefaultPolisherRate();
            }
        }

        if (rate == null) {

            throw new RuntimeException(
                    "Rate not configured for selected Rate Type.");
        }

        return rate;
    }
}
