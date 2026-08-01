package com.furniture.FurnitureManagement.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.dto.WorkEntryRequest;
import com.furniture.FurnitureManagement.entity.ProductDesign;
import com.furniture.FurnitureManagement.entity.ProductRate;
import com.furniture.FurnitureManagement.entity.RateType;
import com.furniture.FurnitureManagement.entity.WorkEntry;
import com.furniture.FurnitureManagement.entity.Worker;
import com.furniture.FurnitureManagement.enums.FinishType;
import com.furniture.FurnitureManagement.enums.ShipmentStatus;
import com.furniture.FurnitureManagement.enums.WorkerRole;
import com.furniture.FurnitureManagement.repository.ProductDesignRepository;
import com.furniture.FurnitureManagement.repository.ProductRateRepository;
import com.furniture.FurnitureManagement.repository.RateTypeRepository;
import com.furniture.FurnitureManagement.repository.WorkEntryRepository;
import com.furniture.FurnitureManagement.repository.WorkerRepository;

@Service
public class WorkEntryService {

    private final WorkEntryRepository workEntryRepository;

    private final WorkerRepository workerRepository;

    private final ProductDesignRepository designRepository;

    private final RateTypeRepository rateTypeRepository;

    private final ProductRateRepository productRateRepository;

    public WorkEntryService(
            WorkEntryRepository workEntryRepository,
            WorkerRepository workerRepository,
            ProductDesignRepository designRepository,
            RateTypeRepository rateTypeRepository,
            ProductRateRepository productRateRepository) {

        this.workEntryRepository = workEntryRepository;
        this.workerRepository = workerRepository;
        this.designRepository = designRepository;
        this.rateTypeRepository = rateTypeRepository;
        this.productRateRepository = productRateRepository;
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

            entry.setRemainingQuantity(
                    quantity);
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

    /**
     * All batches (READY or PARTIALLY_SHIPPED) that still have quantity
     * left to ship, each tagged with the worker who made it. This is the
     * real "ready stock" data - one row per worker's batch.
     */
    public List<WorkEntry> getAvailableBatches() {

        return workEntryRepository
                .findAllAvailableBatches();
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

            int oldRemaining =
                    entry.getRemainingQuantity() != null
                    ? entry.getRemainingQuantity()
                    : oldQuantity;

            int alreadyShipped =
                    Math.max(
                            oldQuantity - oldRemaining,
                            0);

            int newRemaining =
                    Math.max(
                            newQuantity - alreadyShipped,
                            0);

            entry.setRemainingQuantity(
                    newRemaining);

            entry.setShipmentStatus(
                    newRemaining == 0
                    ? ShipmentStatus.SHIPPED
                    : newRemaining == newQuantity
                    ? ShipmentStatus.READY
                    : ShipmentStatus.PARTIALLY_SHIPPED);

            savedEntry =
                    workEntryRepository
                    .save(entry);
        }

        return savedEntry;
    }

    /**
     * Deducts the given quantity from a specific worker's ready-stock batch
     * (WorkEntry) when that batch is chosen for a shipment. This is what
     * links a shipment back to the exact worker who made the item.
     */
    public WorkEntry shipQuantityFromWorkEntry(
            Long workEntryId,
            int shipQuantity) {

        WorkEntry entry =
                workEntryRepository
                .findById(workEntryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Work Entry not found"));

        if (entry.isCancelled()) {

            throw new RuntimeException(
                    "Cannot ship from a cancelled work entry");
        }

        Integer remaining = entry.getRemainingQuantity();

        if (remaining == null
                || shipQuantity <= 0
                || shipQuantity > remaining) {

            throw new RuntimeException(
                    "Insufficient ready stock in batch #"
                            + entry.getId()
                            + " for "
                            + entry.getDesign().getDesignName()
                            + " (worker: "
                            + entry.getWorker().getName()
                            + ")");
        }

        int newRemaining = remaining - shipQuantity;

        entry.setRemainingQuantity(newRemaining);

        entry.setShipmentStatus(
                newRemaining == 0
                ? ShipmentStatus.SHIPPED
                : newRemaining == entry.getQuantity()
                ? ShipmentStatus.READY
                : ShipmentStatus.PARTIALLY_SHIPPED);

        return workEntryRepository.save(entry);
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

            // Only the still-unshipped part of this batch is removed from
            // future availability. Whatever has already shipped stays
            // correctly recorded on those past shipments regardless.
            entry.setRemainingQuantity(0);

            entry.setShipmentStatus(
                    ShipmentStatus.SHIPPED);
        }

        return workEntryRepository
                .save(entry);
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