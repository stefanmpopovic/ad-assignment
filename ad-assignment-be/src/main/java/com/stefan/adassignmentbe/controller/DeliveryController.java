package com.stefan.adassignmentbe.controller;

import com.stefan.adassignmentbe.model.DeliveryRequestDTO;
import com.stefan.adassignmentbe.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDate;

@RestController
public class DeliveryController {

    @Autowired
    private final DeliveryService deliveryService;

    @Autowired
    private final Clock clock;

    public DeliveryController(DeliveryService deliveryService, Clock clock) {
        this.deliveryService = deliveryService;
        this.clock = clock;
    }

    @GetMapping(path = "/delivery-date")
    public LocalDate calculateDeliveryDate(@RequestParam Long productId,
                                           @RequestParam Integer quantity,
                                           @RequestParam(required = false) Boolean expressDelivery) {
        DeliveryRequestDTO deliveryRequestDTO = DeliveryRequestDTO.of(productId, quantity, expressDelivery);
        LocalDate now = LocalDate.now(clock);
        return deliveryService.calculateDeliveryDate(deliveryRequestDTO, now);
    }
}
