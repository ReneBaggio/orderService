package com.orders.domain.services.strategies.mx;

import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.strategies.OrderTaxCalculationStrategy;

import java.time.LocalDate;

public class MXTaxCalculationStrategy implements OrderTaxCalculationStrategy {
    @Override
    public double calculateTaxAmount(Order order, OrderCalculationContext context) {
        return order.getSubTotal() * context.vat();
    }
}
