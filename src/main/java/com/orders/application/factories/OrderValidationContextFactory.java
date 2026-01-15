package com.orders.application.factories;

import com.orders.domain.services.contexts.OrderValidationContext;

public final class OrderValidationContextFactory {

    //We use a Port to get the info from the Database
    //private final CustomerPort customerPort;

    public OrderValidationContext create(String countryCode) {
        boolean active = true; //this should come from database
        return new OrderValidationContext(active);
    }
}
