package com.orderservice.domain.services.strategies.mx;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.services.contexts.TaxCalculationContext;
import com.orderservice.domain.services.strategies.OrderTaxCalculationStrategy;

public class MXTaxCalculationStrategy implements OrderTaxCalculationStrategy {

    @Override
    public double resolveTax(Order order, TaxCalculationContext context) {
        return order.getSubTotal() * context.getTaxRate();
    }
}
