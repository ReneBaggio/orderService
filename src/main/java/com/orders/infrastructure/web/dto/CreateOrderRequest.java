package com.orders.infrastructure.web.dto;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;
import java.util.List;

public class CreateOrderRequest {

    @Getter @Setter
    @NotNull(message = "customerId is required")
    private String customerId;

    @Getter @Setter
    @NotNull(message = "currency is required")
    private Currency currency;

    @Getter @Setter
    @NotEmpty(message = "Order must contain at least one item")
    private List<@Valid CreateOrderRequestItem> items;

    public Order toDomain() {
        List<OrderItem> domainItems = items.stream()
                .map(item ->
                        new OrderItem(item.getProductId(), item.getQuantity(), item.getPrice()))
                .toList();
        return Order.create(customerId, currency, domainItems);
    }
}

