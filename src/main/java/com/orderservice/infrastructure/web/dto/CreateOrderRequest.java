package com.orderservice.infrastructure.web.dto;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.model.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateOrderRequest {

    @NotNull(message = "customerId is required")
    private String customerId;

    @NotEmpty(message = "Order must contain at least one item")
    private List<@Valid CreateOrderRequestItem> items;

    public Order toDomain() {
        List<OrderItem> domainItems = items.stream()
                .map(item ->
                        new OrderItem(item.getProductId(), item.getQuantity(), item.getPrice()))
                .toList();
        return Order.create(customerId, domainItems);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<CreateOrderRequestItem> getItems() {
        return items;
    }

    public void setItems(List<CreateOrderRequestItem> items) {
        this.items = items;
    }
}

