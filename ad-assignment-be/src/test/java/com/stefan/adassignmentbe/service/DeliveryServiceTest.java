package com.stefan.adassignmentbe.service;

import com.stefan.adassignmentbe.model.DeliveryRequestDTO;
import com.stefan.adassignmentbe.model.DeliveryTime;
import com.stefan.adassignmentbe.model.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    private static final LocalDate START_DATE = LocalDate.parse("2021-01-03");

    @Mock
    private ProductService productService;

    @InjectMocks
    private DeliveryService deliveryService;

    @Test
    void test_calculateDeliveryDate_whenProductNotFound_shouldThrowException() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(5);

        when(productService.findById(5L)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> deliveryService.calculateDeliveryDate(request, START_DATE));
    }

    @Test
    void test_calculateDeliveryDate_whenQuantityIsZero_shouldThrowException() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(0);

        when(productService.findById(5L)).thenReturn(new ProductDTO());

        assertThrows(RuntimeException.class, () -> deliveryService.calculateDeliveryDate(request, START_DATE));
    }

    @Test
    void test_calculateDeliveryDate_case1() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(2);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(5L);
        productDTO.setName("Test Product");
        productDTO.setUnit("kg");

        productDTO.setDeliveryTimes(List.of(
                createDeliveryTime(5, 1),
                createDeliveryTime(20, 2),
                createDeliveryTime(50, 3)
        ));

        when(productService.findById(5L)).thenReturn(productDTO);

        LocalDate result = deliveryService.calculateDeliveryDate(request, START_DATE);

        assertEquals(LocalDate.parse("2021-01-04"), result);
    }

    @Test
    void test_calculateDeliveryDate_case2() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(13);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(5L);
        productDTO.setName("Test Product");
        productDTO.setUnit("kg");

        productDTO.setDeliveryTimes(List.of(
                createDeliveryTime(5, 1),
                createDeliveryTime(20, 2),
                createDeliveryTime(50, 3)
        ));

        when(productService.findById(5L)).thenReturn(productDTO);

        LocalDate result = deliveryService.calculateDeliveryDate(request, START_DATE);

        assertEquals(LocalDate.parse("2021-01-05"), result);
    }

    @Test
    void test_calculateDeliveryDate_case3() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(43);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(5L);
        productDTO.setName("Test Product");
        productDTO.setUnit("kg");

        productDTO.setDeliveryTimes(List.of(
                createDeliveryTime(5, 1),
                createDeliveryTime(20, 2),
                createDeliveryTime(50, 3)
        ));

        when(productService.findById(5L)).thenReturn(productDTO);

        LocalDate result = deliveryService.calculateDeliveryDate(request, START_DATE);

        assertEquals(LocalDate.parse("2021-01-06"), result);
    }

    @Test
    void test_calculateDeliveryDate_whenQuantityIsGreaterThanAllMaximumQuantities_shouldSumDays() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(67);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(5L);
        productDTO.setName("Test Product");
        productDTO.setUnit("kg");

        productDTO.setDeliveryTimes(List.of(
                createDeliveryTime(5, 1),
                createDeliveryTime(20, 2),
                createDeliveryTime(50, 3)
        ));

        when(productService.findById(5L)).thenReturn(productDTO);

        LocalDate result = deliveryService.calculateDeliveryDate(request, START_DATE);

        // Quantity 67 = 50 (3 days) + 17 (2 days) = 5 days
        assertEquals(LocalDate.parse("2021-01-08"), result);
    }

    @Test
    void test_calculateDeliveryTime_whenExpressDelivery_shouldLowerTimeByOneDay() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(13);
        request.setExpressDelivery(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(5L);
        productDTO.setName("Test Product");
        productDTO.setUnit("kg");

        productDTO.setDeliveryTimes(List.of(
                createDeliveryTime(5, 1),
                createDeliveryTime(20, 2),
                createDeliveryTime(50, 3)
        ));

        when(productService.findById(5L)).thenReturn(productDTO);

        LocalDate result = deliveryService.calculateDeliveryDate(request, START_DATE);

        assertEquals(LocalDate.parse("2021-01-04"), result);
    }

    @Test
    void test_calculateDeliveryTime_whenExpressDeliveryAndOnlyOneDay_shouldNotLowerTime() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(2);
        request.setExpressDelivery(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(5L);
        productDTO.setName("Test Product");
        productDTO.setUnit("kg");

        productDTO.setDeliveryTimes(List.of(
                createDeliveryTime(5, 1),
                createDeliveryTime(20, 2),
                createDeliveryTime(50, 3)
        ));

        when(productService.findById(5L)).thenReturn(productDTO);

        LocalDate result = deliveryService.calculateDeliveryDate(request, START_DATE);

        assertEquals(LocalDate.parse("2021-01-04"), result);
    }

    @Test
    void test_calculateDeliveryDate_shouldOnlyCalculateBusinessDays() {
        DeliveryRequestDTO request = new DeliveryRequestDTO();
        request.setProductId(5L);
        request.setQuantity(17);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(5L);
        productDTO.setName("Test Product");
        productDTO.setUnit("kg");

        productDTO.setDeliveryTimes(List.of(
                createDeliveryTime(5, 1),
                createDeliveryTime(20, 2),
                createDeliveryTime(50, 3)
        ));

        // 2021-01-08 is a Friday
        LocalDate startingDateBeforeWeekend = LocalDate.parse("2021-01-08");

        when(productService.findById(5L)).thenReturn(productDTO);

        LocalDate result = deliveryService.calculateDeliveryDate(request, startingDateBeforeWeekend);

        assertEquals(LocalDate.parse("2021-01-12"), result);
    }

    private DeliveryTime createDeliveryTime(int maxQuantity, int days) {
        DeliveryTime deliveryTime = new DeliveryTime();
        deliveryTime.setMaxQuantity(maxQuantity);
        deliveryTime.setDays(days);
        return deliveryTime;
    }
}
