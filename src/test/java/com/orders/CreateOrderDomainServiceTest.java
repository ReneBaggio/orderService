package com.orders;

import com.orders.domain.models.Order;
import com.orders.domain.models.OrderItem;
import com.orders.domain.services.CreateOrderDomainService;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.contexts.OrderValidationContext;
import com.orders.domain.services.strategies.mx.MXTaxCalculationStrategy;
import com.orders.domain.services.strategies.us.USTaxCalculationStrategy;
import com.orders.domain.violations.BusinessRuleViolation;
import com.orders.testdoubles.FakeTaxStrategyResolver;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderDomainServiceTest {

	@Test
	void createOrder_shouldThrowBusinessRuleViolation_whenCustomerInactive() {
		var resolver = new FakeTaxStrategyResolver(Map.of(
				"MX", new MXTaxCalculationStrategy(),
				"US", new USTaxCalculationStrategy()
		));

		var domainService = new CreateOrderDomainService(resolver);

		Order order = Order.create(
				"cust-1",
				Currency.getInstance("MXN"),
				List.of(
						new OrderItem("p1", 1, 10.0)
				)
		);

		var vctx = new OrderValidationContext(false);
		var cctx = new OrderCalculationContext("MX", 0.16);

		BusinessRuleViolation ex = assertThrows(BusinessRuleViolation.class,
				() -> domainService.execute(order, vctx, cctx));

		assertEquals("CUSTOMER_INACTIVE", ex.getRuleResult().errorCode());
	}

	@Test
	void createOrder_shouldCalculateSubtotalTaxTotal_whenValid() {
		var resolver = new FakeTaxStrategyResolver(Map.of(
				"MX", new MXTaxCalculationStrategy(),
				"US", new USTaxCalculationStrategy()
		));

		var domainService = new CreateOrderDomainService(resolver);

		Order draft = Order.create(
				"cust-1",
				Currency.getInstance("MXN"),
				List.of(
						new OrderItem("p1", 2, 10.0),
						new OrderItem("p2", 1, 5.0)
				)
		);

		var vctx = new OrderValidationContext(true);
		var cctx = new OrderCalculationContext("MX", 0.16);

		Order calculated = domainService.execute(draft, vctx, cctx);

		assertEquals(3, calculated.getNumOfItems());
		assertEquals(25.0, calculated.getSubTotal(), 0.0001);
		assertEquals(25.0 * 0.16, calculated.getTax(), 0.0001);
		assertEquals(25.0 + (25.0 * 0.16), calculated.getTotal(), 0.0001);

		//testing immutability
		assertEquals(0, draft.getNumOfItems());
		assertEquals(0.0, draft.getSubTotal(), 0.0001);
		assertEquals(0.0, draft.getTax(), 0.0001);
		assertEquals(0.0, draft.getSubTotal(), 0.0001);
	}

}
