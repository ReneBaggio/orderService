package com.orders.infrastructure.adapters.resolvers;

import com.orders.domain.ports.OrderTaxStrategyResolver;
import com.orders.domain.services.strategies.OrderTaxCalculationStrategy;

import java.util.Map;

public final class MapTaxStrategyResolver implements OrderTaxStrategyResolver {

    private final Map<String, OrderTaxCalculationStrategy> taxStrategiesByCountry;

    public MapTaxStrategyResolver(Map<String, OrderTaxCalculationStrategy> taxStrategiesByCountry) {
        this.taxStrategiesByCountry = taxStrategiesByCountry;
    }

    @Override
    public OrderTaxCalculationStrategy resolve(String countryCode) {
        var strategy = taxStrategiesByCountry.get(countryCode);
        if (strategy == null) {
            throw new IllegalArgumentException("No Tax Strategy found for country code " + countryCode);
        }
        return strategy;
    }
}
