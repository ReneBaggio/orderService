package com.orders.rules.calculations;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderItem;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.rules.calculations.OrderTaxCalculationRule;
import com.orders.domain.services.strategies.mx.MXTaxCalculationStrategy;
import com.orders.domain.services.strategies.us.USTaxCalculationStrategy;
import com.orders.testdoubles.FakeTaxStrategyResolver;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTaxCalculationRuleTest {

    @Test
    void taxRule_shouldApplyMXStrategy_whenCountryIsMX() {
        var resolver = new FakeTaxStrategyResolver(Map.of(
                "MX", new MXTaxCalculationStrategy(),
                "US", new USTaxCalculationStrategy()
        ));

        OrderTaxCalculationRule rule = new OrderTaxCalculationRule(resolver);

        Order order = Order.create(
                "cust-1",
                Currency.getInstance("MXN"),
                List.of(
                        new OrderItem("p1", 2, 10.0)
                )
        );

        Order withSubTotal = order.withSubTotal(20.0);
        Order after = rule.calculate(withSubTotal, new OrderCalculationContext("MX", 0.16));
        assertEquals(20.0 * 0.16, after.getTax(), 0.0001);
    }

    @Test
    void taxRule_shouldApplyUSStrategy_whenCountryIsUS() {
        var resolver = new FakeTaxStrategyResolver(Map.of(
                "MX", new MXTaxCalculationStrategy(),
                "US", new USTaxCalculationStrategy()
        ));

        OrderTaxCalculationRule rule = new OrderTaxCalculationRule(resolver);

        Order order = Order.create(
                "cust-1",
                Currency.getInstance("MXN"),
                List.of(
                        new OrderItem("p1", 2, 10.0)
                )
        );

        Order withSubTotal = order.withSubTotal(20.0);
        Order after = rule.calculate(withSubTotal, new OrderCalculationContext("US", 0.08));
        assertEquals(20.0 * 0.08, after.getTax(), 0.0001);
    }
}
