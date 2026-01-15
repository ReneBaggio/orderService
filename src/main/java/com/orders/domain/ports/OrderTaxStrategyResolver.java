package com.orders.domain.ports;

import com.orders.domain.services.strategies.OrderTaxCalculationStrategy;

public interface OrderTaxStrategyResolver {
    OrderTaxCalculationStrategy resolve(String countryCode);
}
