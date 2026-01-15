package com.orders.rules.invariants;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderItem;
import com.orders.domain.violations.DomainInvariantViolation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Currency;
import java.util.List;

public class OrderInvariantsTest {

    @Test
    void ensureInvariants_shouldFail_whenNoItems() {
        var ex = assertThrows(DomainInvariantViolation.class, () ->
                Order.create(
                        "cust-1",
                        Currency.getInstance("USD"),
                        List.of()
                )
        );

        assertEquals("ORDER_EMPTY", ex.getRuleResult().errorCode());
    }

    @Test
    void ensureInvariants_shouldFail_whenQuantityIsZeroOrNegative() {
        var ex = assertThrows(DomainInvariantViolation.class, () ->
                Order.create(
                        "cust-1",
                        null,
                        List.of(new OrderItem("p1", 0, 10.0))
                )
        );
        assertEquals("QTY_INVALID", ex.getRuleResult().errorCode());
    }

    @Test
    void ensureInvariants_shouldFail_whenNoCurrency() {
        var ex = assertThrows(DomainInvariantViolation.class, () ->
                Order.create(
                        "cust-1",
                        null,
                        List.of(new OrderItem("p1", 1, 10.0))
                )
        );

        assertEquals("CURRENCY_MISSING", ex.getRuleResult().errorCode());
    }

    @Test
    void ensureInvariants_shouldPass_whenValid() {
        var order = Order.create(
                "cust-1",
                Currency.getInstance("USD"),
                List.of(new OrderItem("p1", 1, 10.0))
        );
        assertInstanceOf(Order.class, order);
    }
}
