package com.orders.domain.services.strategies.us;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.strategies.OrderTaxCalculationStrategy;

import java.time.LocalDate;

public class USTaxCalculationStrategy implements OrderTaxCalculationStrategy {
    @Override
    public double calculateTaxAmount(Order order, OrderCalculationContext context) {
        return order.getSubTotal() * context.vat();
    }
}
