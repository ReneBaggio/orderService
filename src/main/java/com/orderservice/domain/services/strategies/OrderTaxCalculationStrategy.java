package com.orderservice.domain.services.strategies;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.services.contexts.TaxCalculationContext;

public interface OrderTaxCalculationStrategy {
    public double resolveTax(Order order, TaxCalculationContext context);
}
