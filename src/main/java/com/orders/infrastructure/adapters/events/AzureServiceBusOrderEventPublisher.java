package com.orders.infrastructure.adapters.events;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.orders.domain.events.DomainEvent;
import com.orders.domain.events.OrderCreatedEvent;
import com.orders.domain.ports.EventPublisher;
import tools.jackson.databind.ObjectMapper;

public class AzureServiceBusOrderEventPublisher implements EventPublisher {

   private final ServiceBusSenderClient sender;
   private final ObjectMapper mapper = new ObjectMapper();

   public AzureServiceBusOrderEventPublisher(String connectionString, String queueName) {
       this.sender = new ServiceBusClientBuilder()
               .connectionString(connectionString)
               .sender()
               .queueName(queueName)
               .buildClient();
   }

    @Override
    public void publish(DomainEvent event) {
       if(event instanceof OrderCreatedEvent) {
           try {
               String json = mapper.writeValueAsString(event);
               ServiceBusMessage message = new ServiceBusMessage(json);
               sender.sendMessage(message);
           } catch (Exception e) {
               throw new RuntimeException("Failed to publish OrderCreatedEvent", e);
           }
       }
    }
}
