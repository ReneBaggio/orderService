package com.orderservice.domain.services.contexts;

import com.orderservice.domain.vo.Country;
import lombok.Getter;

public class TaxCalculationContext implements RuleContext {

    @Getter
    private final double taxRate;
    @Getter
    private final Country country;
    @Getter
    private final double discount;

    public TaxCalculationContext(double taxRate, Country country, double discount) {
        this.taxRate = taxRate;
        this.country  = country;
        this.discount = discount;
    }
}
