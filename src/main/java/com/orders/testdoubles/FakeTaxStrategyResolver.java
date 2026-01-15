package com.orders.testdoubles;

import com.orders.domain.ports.OrderTaxStrategyResolver;
import com.orders.domain.services.strategies.OrderTaxCalculationStrategy;

import java.util.Map;

public class FakeTaxStrategyResolver implements OrderTaxStrategyResolver {

    private final Map<String, OrderTaxCalculationStrategy> map;

    public FakeTaxStrategyResolver(Map<String, OrderTaxCalculationStrategy> map) {
        this.map = map;
    }

    @Override
    public OrderTaxCalculationStrategy resolve(String countryCode) {
        var strategy = map.get(countryCode);
        if(strategy == null) throw  new IllegalArgumentException("No tax strategy for: " + countryCode);
        return strategy;
    }
}
