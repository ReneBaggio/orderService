package com.orderservice.domain.services.policies;

import com.orderservice.domain.model.Order;
import com.orderservice.domain.services.contexts.RuleContext;
import com.orderservice.domain.services.specifications.CalculationRule;

import java.util.Queue;

public class CalculateCompositeRule<T extends RuleContext> implements CalculationRule<T> {

    private final Queue<CalculationRule<T>> rules;

    public CalculateCompositeRule(Queue<CalculationRule<T>> rules) {
        this.rules = rules;
    }

    @Override
    public Order calculate(Order order, T context) {
        Order current = order;

        for(CalculationRule<T> rule : rules) {
            current = rule.calculate(current, context);
        }

        return current;
    }
}
