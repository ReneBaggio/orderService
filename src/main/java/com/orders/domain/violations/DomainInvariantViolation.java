package com.orders.domain.violations;

import com.orders.domain.services.rules.RuleResult;
import lombok.Getter;

public final class DomainInvariantViolation extends RuntimeException {

    @Getter
    private final RuleResult ruleResult;

    public DomainInvariantViolation(RuleResult ruleResult) {
        super(ruleResult.message());
        this.ruleResult = ruleResult;
    }
}
