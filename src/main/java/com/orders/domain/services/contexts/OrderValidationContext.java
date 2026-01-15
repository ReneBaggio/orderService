package com.orders.domain.services.contexts;

public record OrderValidationContext(boolean customerActive) implements RuleContext {
}
