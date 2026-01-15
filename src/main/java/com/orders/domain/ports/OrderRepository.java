package com.orders.domain.ports;

import com.orders.domain.models.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(UUID id);
}
