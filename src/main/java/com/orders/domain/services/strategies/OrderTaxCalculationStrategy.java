package com.orders.domain.services.strategies;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.OrderCalculationContext;

public interface OrderTaxCalculationStrategy {
    double calculateTaxAmount(Order order, OrderCalculationContext context);
}
