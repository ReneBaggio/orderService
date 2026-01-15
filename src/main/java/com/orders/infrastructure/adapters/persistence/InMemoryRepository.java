package com.orders.infrastructure.adapters.persistence;

import com.orders.domain.models.Order;
import com.orders.domain.ports.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRepository implements OrderRepository {

    private final Map<UUID, Order> store = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        store.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }
}
