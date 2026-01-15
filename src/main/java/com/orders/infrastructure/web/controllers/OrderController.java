package com.orders.infrastructure.web.controllers;

import com.orders.application.CreateOrderApplicationService;
import com.orders.infrastructure.web.dto.CreateOrderRequest;
import com.orders.shared.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderApplicationService applicationService;

    public OrderController(CreateOrderApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrderRequest request) {

        Result<?> result = applicationService.execute(request);

        return switch (result.getType()) {
            case SUCCESS -> ResponseEntity.ok(result);
            case BUSINESS_ERROR -> ResponseEntity.unprocessableContent().body(result);
            case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            case VALIDATION_ERROR -> ResponseEntity.badRequest().body(result);
        };
    }
}
