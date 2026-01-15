package com.orders.infrastructure.config;

import com.orders.domain.services.strategies.OrderTaxCalculationStrategy;
import com.orders.domain.services.strategies.mx.MXTaxCalculationStrategy;
import com.orders.domain.services.strategies.us.USTaxCalculationStrategy;
import com.orders.infrastructure.adapters.resolvers.MapTaxStrategyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class TaxStrategyConfig {

    @Bean
    public Map<String, OrderTaxCalculationStrategy> taxStrategiesByCountry() {
        return Map.of(
                "MX", new MXTaxCalculationStrategy(),
                "US", new USTaxCalculationStrategy()
        );
    }

    @Bean
    public MapTaxStrategyResolver taxStrategyResolver(Map<String, OrderTaxCalculationStrategy> taxStrategiesByCountry) {
        return new MapTaxStrategyResolver(taxStrategiesByCountry);
    }
}
