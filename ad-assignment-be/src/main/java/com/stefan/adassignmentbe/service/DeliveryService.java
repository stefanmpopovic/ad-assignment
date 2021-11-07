package com.stefan.adassignmentbe.service;

import com.stefan.adassignmentbe.model.DeliveryRequestDTO;
import com.stefan.adassignmentbe.model.DeliveryTime;
import com.stefan.adassignmentbe.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
public class DeliveryService {

    public static final int EXPRESS_DELIVERY_DAYS_REDUCTION = 1;

    public static final List<DayOfWeek> NON_BUSINESS_DAYS = List.of(
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
    );

    @Autowired
    private final ProductService productService;

    public DeliveryService(ProductService productService) {
        this.productService = productService;
    }

    public LocalDate calculateDeliveryDate(DeliveryRequestDTO deliveryRequestDTO, LocalDate startDate) {
        ProductDTO productDTO = productService.findById(deliveryRequestDTO.getProductId());

        if (deliveryRequestDTO.getQuantity() <= 1) {
            throw new RuntimeException("Quantity must be above 0");
        }

        int quantityRemaining = deliveryRequestDTO.getQuantity();
        int totalDays = 0;

        while (quantityRemaining > 0) {
            for (int i = 0; i < productDTO.getDeliveryTimes().size(); i++) {
                DeliveryTime deliveryTime = productDTO.getDeliveryTimes().get(i);

                if (quantityRemaining <= deliveryTime.getMaxQuantity() || i == productDTO.getDeliveryTimes().size() - 1) {
                    quantityRemaining -= deliveryTime.getMaxQuantity();
                    totalDays += deliveryTime.getDays();
                    break;
                }
            }
        }

        if (deliveryRequestDTO.isExpressDelivery() && totalDays > 1) {
            totalDays -= EXPRESS_DELIVERY_DAYS_REDUCTION;
        }

        return addBusinessDays(startDate, totalDays);
    }

    private LocalDate addBusinessDays(LocalDate startDate, int days) {
        int daysAdded = 0;
        LocalDate date = LocalDate.from(startDate);

        while (daysAdded < days) {
            date = date.plusDays(1);

            if (!NON_BUSINESS_DAYS.contains(date.getDayOfWeek())) {
                daysAdded++;
            }
        }

        return date;
    }
}
