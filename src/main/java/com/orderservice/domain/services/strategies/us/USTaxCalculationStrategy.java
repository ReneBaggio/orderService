package com.orderservice.domain.services.strategies.us;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.services.contexts.TaxCalculationContext;
import com.orderservice.domain.services.strategies.OrderTaxCalculationStrategy;

public class USTaxCalculationStrategy implements OrderTaxCalculationStrategy {
    @Override
    public double resolveTax(Order order, TaxCalculationContext context) {
        return order.getSubTotal() *
                (context.getTaxRate() - (context.getTaxRate() * context.getDiscount()));
    }
}
