package com.orders.application.factories;

import com.orders.domain.services.contexts.OrderCalculationContext;

public final class OrderCalculationContextFactory {

    //We use a Port to get the info from the Database
    //private final VatPort vatPort;

    public OrderCalculationContext create(String countryCode) {
        double vat = 0.16; //we get this from database
        return new OrderCalculationContext(countryCode, vat);
    }
}
