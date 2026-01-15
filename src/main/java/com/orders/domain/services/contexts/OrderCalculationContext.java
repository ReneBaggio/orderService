package com.orders.domain.services.contexts;

public record OrderCalculationContext(String countryCode, double vat) implements RuleContext {
}
