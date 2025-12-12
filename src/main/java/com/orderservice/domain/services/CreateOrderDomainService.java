package com.orderservice.domain.services;

import com.orderservice.domain.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateOrderDomainService {

    private final List<String> businessErrors = new ArrayList<>();

    public boolean validate(Order order) {
        if(order.getItems().isEmpty())
            businessErrors.add("Order must have at least one item");

        return !businessErrors.isEmpty();
    }

    public List<String> getBusinessErrors() {
        return List.copyOf(businessErrors);
    }
}
