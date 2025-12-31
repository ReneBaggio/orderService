package com.orderservice.rules;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.model.OrderItem;
import com.orderservice.domain.services.contexts.TaxCalculationContext;
import com.orderservice.domain.services.strategies.OrderTaxCalculationStrategy;
import com.orderservice.domain.services.strategies.mx.MXTaxCalculationStrategy;
import com.orderservice.domain.services.strategies.us.USTaxCalculationStrategy;
import com.orderservice.domain.vo.Country;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTotalCalculationRuleTest {

    @Test
    void shouldCalculateTotalWithMexicoTaxes() {
        List<OrderItem> orderItems = List.of(
        new OrderItem("1", 1, 50),
        new OrderItem("2", 1, 50));

        Order order = Order.restore(UUID.randomUUID(), "1", orderItems,0);
        var country = new Country("MX", "México");
        TaxCalculationContext context = new TaxCalculationContext(0.16, country, 0.0);
        OrderTaxCalculationStrategy strategy = new MXTaxCalculationStrategy();

        double total = strategy.resolveTax(order, context);

        assertEquals(16, total);
    }

    @Test
    void shouldCalculateTotalWithUnitedStatesTaxes() {
        List<OrderItem> orderItems = List.of(
                new OrderItem("1", 1, 50),
                new OrderItem("2", 1, 50));

        Order order = Order.restore(UUID.randomUUID(), "1", orderItems,0);
        var country = new Country("US", "United States");
        TaxCalculationContext context = new TaxCalculationContext(0.08, country, 0.1);
        OrderTaxCalculationStrategy strategy = new USTaxCalculationStrategy();

        double total = strategy.resolveTax(order, context);

        assertEquals(7.200000000000001, total);
    }
}
