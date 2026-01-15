package com.orders.infrastructure.config;

import com.orders.domain.ports.EventPublisher;
import com.orders.infrastructure.adapters.events.AzureServiceBusOrderEventPublisher;
import com.orders.infrastructure.adapters.events.CompositeEventPublisher;
import com.orders.infrastructure.adapters.events.LoggingEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MessagingConfig {

    @Bean
    public EventPublisher eventPublisher(
            @Value("${azure.servicebus.connection-string}") String connectionString,
            @Value("${azure.servicebus.queue}") String queueName) {

        LoggingEventPublisher loggingEventPublisher = new LoggingEventPublisher();
        AzureServiceBusOrderEventPublisher azureServiceBusOrderEventPublisher =
                new AzureServiceBusOrderEventPublisher(connectionString, queueName);

        return new CompositeEventPublisher(
                List.of(loggingEventPublisher, azureServiceBusOrderEventPublisher)
        );
    }
}
