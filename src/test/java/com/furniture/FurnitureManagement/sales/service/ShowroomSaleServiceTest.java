package com.furniture.FurnitureManagement.sales.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleProductRequest;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleRequest;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleResponse;
import com.furniture.FurnitureManagement.sales.entity.ShowroomSale;
import com.furniture.FurnitureManagement.sales.repository.ShowroomSaleRepository;

@ExtendWith(MockitoExtension.class)
class ShowroomSaleServiceTest {

    @Mock
    private ShowroomSaleRepository repository;

    @Mock
    private SaleNotificationService notificationService;

    private ShowroomSaleService service;

    @BeforeEach
    void setUp() {

        service =
                new ShowroomSaleService(repository, notificationService);

        when(repository.save(any(ShowroomSale.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));
    }

    @Test
    void createSaleCalculatesTotalFromProducts() {

        ShowroomSaleRequest request =
                new ShowroomSaleRequest();

        request.setCustomerName("Rahul Sharma");
        request.setCustomerPhone("9999999999");
        request.setLocation("Delhi");
        request.setCategory("Bedroom");
        request.setRemarks("Urgent delivery");
        request.setProducts(
                List.of(
                        product(
                                "Bed",
                                "Bedroom",
                                2,
                                "15000"),
                        product(
                                "Side Table",
                                "Bedroom",
                                1,
                                "4500")));

        ShowroomSaleResponse response =
                service.createSale(request);

        assertThat(response.getTotalAmount())
                .isEqualByComparingTo("34500");

        assertThat(response.getProducts())
                .hasSize(2);

        assertThat(response.getProducts().get(0).getLineTotal())
                .isEqualByComparingTo("30000");

        assertThat(response.getDescription())
                .contains(
                        "Customer: Rahul Sharma",
                        "Bed",
                        "Side Table",
                        "Remarks: Urgent delivery");
    }

    @Test
    void createSaleKeepsLegacyDescriptionAndTotalPayloadWorking() {

        ShowroomSaleRequest request =
                new ShowroomSaleRequest();

        request.setLocation("Mumbai");
        request.setCategory("Living Room");
        request.setDescription("Legacy sofa sale");
        request.setTotalAmount(
                new BigDecimal("12500"));

        ShowroomSaleResponse response =
                service.createSale(request);

        assertThat(response.getCustomerName())
                .isEqualTo("Walk-in Customer");

        assertThat(response.getTotalAmount())
                .isEqualByComparingTo("12500");

        assertThat(response.getProducts())
                .hasSize(1);

        assertThat(response.getProducts().get(0).getProductName())
                .isEqualTo("Legacy sofa sale");
    }

    private ShowroomSaleProductRequest product(
            String name,
            String category,
            int quantity,
            String price) {

        ShowroomSaleProductRequest product =
                new ShowroomSaleProductRequest();

        product.setProductName(name);
        product.setCategory(category);
        product.setQuantity(quantity);
        product.setPrice(
                new BigDecimal(price));

        return product;
    }
}