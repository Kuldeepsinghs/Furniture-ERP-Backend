package com.furniture.FurnitureManagement.sales.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.sales.dto.SalesDashboardResponse;
import com.furniture.FurnitureManagement.sales.dto.SalesGroupAmountResponse;
import com.furniture.FurnitureManagement.sales.dto.SalesReportResponse;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSalePageResponse;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleProductRequest;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleProductResponse;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleRequest;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleResponse;
import com.furniture.FurnitureManagement.sales.dto.WeeklySalesChartResponse;
import com.furniture.FurnitureManagement.sales.entity.SaleStatus;
import com.furniture.FurnitureManagement.sales.entity.ShowroomSale;
import com.furniture.FurnitureManagement.sales.entity.ShowroomSaleProduct;
import com.furniture.FurnitureManagement.sales.repository.ShowroomSaleRepository;

import jakarta.persistence.criteria.Predicate;

@Service
public class ShowroomSaleService {

    private final ShowroomSaleRepository repository;

    public ShowroomSaleService(
            ShowroomSaleRepository repository) {

        this.repository = repository;
    }

    public ShowroomSaleResponse createSale(
            ShowroomSaleRequest request) {

        ShowroomSale sale =
                new ShowroomSale();

        applyRequest(
                sale,
                request);

        sale.setCreatedBy(
                getCurrentUsername());

        sale.setStatus(
                SaleStatus.ACTIVE);

        return toResponse(
                repository.save(sale));
    }

    public ShowroomSaleResponse updateSale(
            Long id,
            ShowroomSaleRequest request) {

        ShowroomSale sale =
                getActiveSale(id);

        applyRequest(
                sale,
                request);

        return toResponse(
                repository.save(sale));
    }

    public ShowroomSaleResponse deleteSale(
            Long id) {

        ShowroomSale sale =
                getActiveSale(id);

        sale.setStatus(
                SaleStatus.DELETED);

        return toResponse(
                repository.save(sale));
    }

    public ShowroomSaleResponse getSaleById(
            Long id) {

        return toResponse(
                getActiveSale(id));
    }

    public ShowroomSalePageResponse getAllSales(
            int page,
            int size,
            String sortBy,
            String sortDir,
            String search) {

        Sort sort =
                "asc".equalsIgnoreCase(sortDir)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort);

        Page<ShowroomSale> sales =
                repository.findAll(
                        (root, query, builder) -> {

                            List<Predicate> predicates =
                                    new ArrayList<>();

                            predicates.add(
                                    builder.equal(
                                            root.get("status"),
                                            SaleStatus.ACTIVE));

                            if (search != null
                                    && !search.isBlank()) {

                                String keyword =
                                        "%"
                                        + search.toLowerCase()
                                        + "%";

                                Predicate textSearch =
                                        builder.or(
                                                builder.like(
                                                        builder.lower(
                                                                root.get("category")),
                                                        keyword),
                                                builder.like(
                                                        builder.lower(
                                                                root.get("location")),
                                                        keyword),
                                                builder.like(
                                                        builder.lower(
                                                                root.get("customerName")),
                                                        keyword),
                                                builder.like(
                                                        builder.lower(
                                                                root.get("customerPhone")),
                                                        keyword),
                                                builder.like(
                                                        builder.lower(
                                                                root.get("remarks")),
                                                        keyword),
                                                builder.like(
                                                        builder.lower(
                                                                root.get("description")),
                                                        keyword));

                                LocalDate searchDate =
                                        parseDate(search);

                                if (searchDate != null) {

                                    predicates.add(
                                            builder.or(
                                                    textSearch,
                                                    builder.between(
                                                            root.get("saleDateTime"),
                                                            searchDate.atStartOfDay(),
                                                            searchDate
                                                            .plusDays(1)
                                                            .atStartOfDay())));
                                } else {

                                    predicates.add(textSearch);
                                }
                            }

                            return builder.and(
                                    predicates.toArray(
                                            new Predicate[0]));
                        },
                        pageable);

        List<ShowroomSaleResponse> content =
                sales.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return new ShowroomSalePageResponse(
                content,
                sales.getTotalElements(),
                sales.getTotalPages(),
                sales.getNumber(),
                sales.getSize());
    }

    public SalesDashboardResponse getDashboard() {

        LocalDate today =
                LocalDate.now();

        LocalDate weekStart =
                today.minusDays(6);

        LocalDate monthStart =
                today.withDayOfMonth(1);

        LocalDate yearStart =
                today.withDayOfYear(1);

        LocalDate tomorrow =
                today.plusDays(1);

        List<ShowroomSaleResponse> recentSales =
                repository
                .findByStatusOrderBySaleDateTimeDesc(
                        SaleStatus.ACTIVE,
                        PageRequest.of(0, 10))
                .stream()
                .map(this::toResponse)
                .toList();

        return new SalesDashboardResponse(
                amountBetween(today, tomorrow),
                amountBetween(weekStart, tomorrow),
                amountBetween(monthStart, tomorrow),
                amountBetween(yearStart, tomorrow),
                recentSales,
                topCategoriesBetween(
                        yearStart,
                        tomorrow,
                        5),
                topLocationsBetween(
                        yearStart,
                        tomorrow,
                        5),
                getWeeklySalesChart(
                        weekStart,
                        tomorrow));
    }

    public SalesReportResponse getTodayReport() {

        LocalDate today =
                LocalDate.now();

        return getReport(
                today,
                today.plusDays(1));
    }

    public SalesReportResponse getWeeklyReport() {

        LocalDate today =
                LocalDate.now();

        return getReport(
                today.minusDays(6),
                today.plusDays(1));
    }

    public SalesReportResponse getMonthlyReport() {

        LocalDate today =
                LocalDate.now();

        return getReport(
                today.withDayOfMonth(1),
                today.plusDays(1));
    }

    public SalesReportResponse getYearlyReport() {

        LocalDate today =
                LocalDate.now();

        return getReport(
                today.withDayOfYear(1),
                today.plusDays(1));
    }

    public SalesReportResponse getCustomReport(
            LocalDate startDate,
            LocalDate endDate) {

        if (startDate == null
                || endDate == null
                || endDate.isBefore(startDate)) {

            throw new RuntimeException(
                    "Invalid date range");
        }

        return getReport(
                startDate,
                endDate.plusDays(1));
    }

    private SalesReportResponse getReport(
            LocalDate startDate,
            LocalDate exclusiveEndDate) {

        BigDecimal totalRevenue =
                amountBetween(
                        startDate,
                        exclusiveEndDate);

        Long numberOfSales =
                repository.countSalesBetween(
                        startDate.atStartOfDay(),
                        exclusiveEndDate.atStartOfDay());

        BigDecimal averageSale =
                numberOfSales != null
                        && numberOfSales > 0
                ? totalRevenue.divide(
                        BigDecimal.valueOf(numberOfSales),
                        2,
                        RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        String topCategory =
                topCategoriesBetween(
                        startDate,
                        exclusiveEndDate,
                        1)
                .stream()
                .findFirst()
                .map(SalesGroupAmountResponse::getName)
                .orElse(null);

        String topLocation =
                topLocationsBetween(
                        startDate,
                        exclusiveEndDate,
                        1)
                .stream()
                .findFirst()
                .map(SalesGroupAmountResponse::getName)
                .orElse(null);

        return new SalesReportResponse(
                totalRevenue,
                numberOfSales,
                averageSale,
                topCategory,
                topLocation);
    }

    private List<WeeklySalesChartResponse> getWeeklySalesChart(
            LocalDate startDate,
            LocalDate exclusiveEndDate) {

        List<WeeklySalesChartResponse> chart =
                new ArrayList<>();

        LocalDate current =
                startDate;

        while (current.isBefore(exclusiveEndDate)) {

            chart.add(
                    new WeeklySalesChartResponse(
                            current,
                            amountBetween(
                                    current,
                                    current.plusDays(1))));

            current =
                    current.plusDays(1);
        }

        return chart;
    }

    private BigDecimal amountBetween(
            LocalDate startDate,
            LocalDate exclusiveEndDate) {

        return repository.getTotalAmountBetween(
                startDate.atStartOfDay(),
                exclusiveEndDate.atStartOfDay());
    }

    private List<SalesGroupAmountResponse> topCategoriesBetween(
            LocalDate startDate,
            LocalDate exclusiveEndDate,
            int limit) {

        return repository.getTopCategoriesBetween(
                startDate.atStartOfDay(),
                exclusiveEndDate.atStartOfDay(),
                PageRequest.of(0, limit));
    }

    private List<SalesGroupAmountResponse> topLocationsBetween(
            LocalDate startDate,
            LocalDate exclusiveEndDate,
            int limit) {

        return repository.getTopLocationsBetween(
                startDate.atStartOfDay(),
                exclusiveEndDate.atStartOfDay(),
                PageRequest.of(0, limit));
    }

    private ShowroomSale getActiveSale(
            Long id) {

        ShowroomSale sale =
                repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sale not found"));

        if (sale.getStatus() != SaleStatus.ACTIVE) {

            throw new RuntimeException(
                    "Sale not found");
        }

        return sale;
    }

    private void applyRequest(
            ShowroomSale sale,
            ShowroomSaleRequest request) {

        sale.setCategory(
                trimRequired(
                        request.getCategory(),
                        "Category is required"));

        sale.setLocation(
                trimRequired(
                        request.getLocation(),
                        "Location is required"));

        sale.setCustomerName(
                resolveCustomerName(request));

        sale.setCustomerPhone(
                trimToNull(
                        request.getCustomerPhone()));

        sale.setRemarks(
                trimToNull(
                        request.getRemarks()));

        List<ShowroomSaleProduct> products =
                resolveProducts(request);

        sale.setTotalAmount(
                calculateTotal(products));

        sale.setProducts(products);

        sale.setDescription(
                buildDescription(
                        sale,
                        request.getDescription()));

        sale.setSaleDateTime(
                request.getSaleDateTime() != null
                ? request.getSaleDateTime()
                : LocalDateTime.now());
    }

    private ShowroomSaleResponse toResponse(
            ShowroomSale sale) {

        List<ShowroomSaleProductResponse> products =
                sale.getProducts()
                .stream()
                .map(product ->
                        new ShowroomSaleProductResponse(
                                product.getProductName(),
                                product.getQuantity(),
                                product.getPrice(),
                                calculateLineTotal(product)))
                .toList();

        return new ShowroomSaleResponse(
                sale.getId(),
                sale.getCategory(),
                sale.getLocation(),
                sale.getCustomerName(),
                sale.getCustomerPhone(),
                products,
                sale.getRemarks(),
                sale.getDescription(),
                sale.getTotalAmount(),
                sale.getSaleDateTime(),
                sale.getCreatedAt(),
                sale.getUpdatedAt(),
                sale.getCreatedBy(),
                sale.getStatus().name());
    }

    private String resolveCustomerName(
            ShowroomSaleRequest request) {

        String customerName =
                trimToNull(
                        request.getCustomerName());

        if (customerName != null) {

            return customerName;
        }

        if (hasProducts(request)) {

            throw new RuntimeException(
                    "Customer Name is required");
        }

        return "Walk-in Customer";
    }

    private List<ShowroomSaleProduct> resolveProducts(
            ShowroomSaleRequest request) {

        if (hasProducts(request)) {

            return request.getProducts()
                    .stream()
                    .map(this::toProduct)
                    .toList();
        }

        if (request.getTotalAmount() == null
                || request.getTotalAmount()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Products are required");
        }

        ShowroomSaleProduct legacyProduct =
                new ShowroomSaleProduct();

        legacyProduct.setProductName(
                trimToNull(
                        request.getDescription()) != null
                ? trimToNull(
                        request.getDescription())
                : request.getCategory());

        legacyProduct.setQuantity(1);

        legacyProduct.setPrice(
                request.getTotalAmount());

        return List.of(legacyProduct);
    }

    private ShowroomSaleProduct toProduct(
            ShowroomSaleProductRequest request) {

        ShowroomSaleProduct product =
                new ShowroomSaleProduct();

        product.setProductName(
                trimRequired(
                        request.getProductName(),
                        "Product Name is required"));

        if (request.getQuantity() == null
                || request.getQuantity() < 1) {

            throw new RuntimeException(
                    "Quantity must be greater than 0");
        }

        if (request.getPrice() == null
                || request.getPrice()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Price must be greater than 0");
        }

        product.setQuantity(
                request.getQuantity());

        product.setPrice(
                request.getPrice());

        return product;
    }

    private BigDecimal calculateTotal(
            List<ShowroomSaleProduct> products) {

        return products
                .stream()
                .map(this::calculateLineTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);
    }

    private BigDecimal calculateLineTotal(
            ShowroomSaleProduct product) {

        return product
                .getPrice()
                .multiply(
                        BigDecimal.valueOf(
                                product.getQuantity()));
    }

    private String buildDescription(
            ShowroomSale sale,
            String legacyDescription) {

        StringBuilder description =
                new StringBuilder();

        description
                .append("Customer: ")
                .append(sale.getCustomerName())
                .append("\n");

        if (sale.getCustomerPhone() != null) {

            description
                    .append("Phone: ")
                    .append(sale.getCustomerPhone())
                    .append("\n");
        }

        description
                .append("Products:\n");

        for (ShowroomSaleProduct product : sale.getProducts()) {

            description
                    .append("- ")
                    .append(product.getProductName())
                    .append(" | Qty: ")
                    .append(product.getQuantity())
                    .append(" | Price: ")
                    .append(product.getPrice())
                    .append(" | Total: ")
                    .append(calculateLineTotal(product))
                    .append("\n");
        }

        if (sale.getRemarks() != null) {

            description
                    .append("Remarks: ")
                    .append(sale.getRemarks())
                    .append("\n");
        } else if (trimToNull(legacyDescription) != null
                && !hasStructuredFields(sale)) {

            description
                    .append("Remarks: ")
                    .append(trimToNull(legacyDescription))
                    .append("\n");
        }

        return description.toString().trim();
    }

    private boolean hasStructuredFields(
            ShowroomSale sale) {

        return sale.getCustomerPhone() != null
                || sale.getRemarks() != null
                || sale.getProducts().size() > 1;
    }

    private boolean hasProducts(
            ShowroomSaleRequest request) {

        return request.getProducts() != null
                && !request.getProducts().isEmpty();
    }

    private String trimRequired(
            String value,
            String message) {

        String trimmed =
                trimToNull(value);

        if (trimmed == null) {

            throw new RuntimeException(message);
        }

        return trimmed;
    }

    private String trimToNull(
            String value) {

        if (value == null
                || value.isBlank()) {

            return null;
        }

        return value.trim();
    }

    private String getCurrentUsername() {

        Authentication authentication =
                SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null) {

            return null;
        }

        return authentication.getName();
    }

    private LocalDate parseDate(
            String value) {

        try {

            return LocalDate.parse(value);
        } catch (Exception ex) {

            return null;
        }
    }
}
