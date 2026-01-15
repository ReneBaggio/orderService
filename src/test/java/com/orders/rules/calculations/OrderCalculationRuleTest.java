package com.orders.rules.calculations;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderItem;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.rules.calculations.OrderNumberOfItemsCalculationRule;
import com.orders.domain.services.rules.calculations.OrderSubtotalCalculationRule;
import com.orders.domain.services.rules.calculations.OrderTotalCalculationRule;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderCalculationRuleTest {

    @Test
    void subtotalRule_shouldComputeSubtotal_fromItems() {
        Order order = Order.create(
                "cust-1",
                Currency.getInstance("USD"),
                List.of(
                        new OrderItem("p1", 2, 10.0),
                        new OrderItem("p2", 1, 5.0)
                )
        );
        var context = new OrderCalculationContext("US", 8.0);
        Order after = new OrderSubtotalCalculationRule().calculate(order, context);

        assertEquals(25.0, after.getSubTotal(), 0.0001);
        //Test immutability
        assertEquals(0.0, order.getSubTotal(), 0.0001);
    }

    @Test
    void numOfItemsRule_shouldComputeTotalQuantity() {
        Order order = Order.create(
                "cust-1",
                Currency.getInstance("USD"),
                List.of(
                        new OrderItem("p1", 2, 10.0),
                        new OrderItem("p2", 1, 5.0)
                )
        );
        var context = new OrderCalculationContext("US", 8.0);
        Order after = new OrderNumberOfItemsCalculationRule().calculate(order, context);

        assertEquals(3, after.getNumOfItems());
        assertEquals(0, order.getNumOfItems());
    }

    @Test
    void totalRule_shouldComputeTotal_subtotalPlusTax() {
        Order order = Order.create(
                "cust-1",
                Currency.getInstance("USD"),
                List.of(
                        new OrderItem("p1", 2, 10.0)
                )
        );
        var context = new OrderCalculationContext("US", 8.0);
        Order withSubtotal = order.withSubTotal(20.0);
        Order withTax = withSubtotal.withTax(4.2);

        Order after = new OrderTotalCalculationRule().calculate(withTax, context);
        assertEquals(24.2, after.getTotal(), 0.0001);
    }

}
