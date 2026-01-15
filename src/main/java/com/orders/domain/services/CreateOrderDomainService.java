package com.orders.domain.services;

import com.orders.domain.ports.OrderTaxStrategyResolver;
import com.orders.domain.events.OrderCreatedEvent;
import com.orders.domain.models.Order;
import com.orders.domain.services.contexts.OrderCalculationContext;
import com.orders.domain.services.contexts.OrderValidationContext;
import com.orders.domain.services.policies.CalculationCompositeRule;
import com.orders.domain.services.policies.ValidationCompositeRule;
import com.orders.domain.services.rules.RuleResult;
import com.orders.domain.services.rules.calculations.OrderNumberOfItemsCalculationRule;
import com.orders.domain.services.rules.calculations.OrderSubtotalCalculationRule;
import com.orders.domain.services.rules.calculations.OrderTaxCalculationRule;
import com.orders.domain.services.rules.calculations.OrderTotalCalculationRule;
import com.orders.domain.services.rules.validations.CustomerMustBeActiveRule;
import com.orders.domain.services.rules.validations.OrderMustBeInDraftStateRule;
import com.orders.domain.services.specifications.CalculationRule;
import com.orders.domain.services.specifications.ValidationRule;
import com.orders.domain.violations.BusinessRuleViolation;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public final class CreateOrderDomainService {

    private final OrderTaxStrategyResolver taxStrategyResolver;

    public CreateOrderDomainService(OrderTaxStrategyResolver taxStrategyResolver) {
        this.taxStrategyResolver = taxStrategyResolver;
    }

    public Order execute(Order order, OrderValidationContext validationContext, OrderCalculationContext calculationContext) {
        //
        Queue<ValidationRule<OrderValidationContext>> validationFlow = new ArrayDeque<>();
        validationFlow.add(new OrderMustBeInDraftStateRule());
        validationFlow.add(new CustomerMustBeActiveRule());

        RuleResult validationFlowResult = new ValidationCompositeRule<>(validationFlow)
                .validate(order, validationContext);

        if(!validationFlowResult.valid()){
            throw new BusinessRuleViolation(validationFlowResult);
        }
        //
        Queue<CalculationRule<OrderCalculationContext>> calculationFlow = new ArrayDeque<>();
        calculationFlow.add(new OrderSubtotalCalculationRule());
        calculationFlow.add(new OrderNumberOfItemsCalculationRule());
        calculationFlow.add(new OrderTaxCalculationRule(taxStrategyResolver));
        calculationFlow.add(new OrderTotalCalculationRule());

        order = new CalculationCompositeRule<>(calculationFlow)
                .calculate(order, calculationContext);

        order.pushDomainEvent(new OrderCreatedEvent(order.getId()));

        return order;
    }
}
