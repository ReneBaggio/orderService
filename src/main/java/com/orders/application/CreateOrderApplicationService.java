package com.orders.application;

import com.orders.application.factories.OrderCalculationContextFactory;
import com.orders.application.factories.OrderValidationContextFactory;
import com.orders.domain.events.DomainEvent;
import com.orders.domain.models.Order;
import com.orders.domain.ports.EventPublisher;
import com.orders.domain.ports.OrderRepository;
import com.orders.domain.services.CreateOrderDomainService;
import com.orders.infrastructure.web.dto.CreateOrderRequest;
import com.orders.shared.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateOrderApplicationService {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;
    private final CreateOrderDomainService createOrderDomainService;
    private final OrderValidationContextFactory validationContextFactory;
    private final OrderCalculationContextFactory calculationContextFactory;

    public CreateOrderApplicationService(OrderRepository orderRepository,
                                         EventPublisher eventPublisher,
                                         CreateOrderDomainService createOrderDomainService,
                                         OrderValidationContextFactory validationContextFactory,
                                         OrderCalculationContextFactory calculationContextFactory) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
        this.createOrderDomainService = createOrderDomainService;
        this.validationContextFactory = validationContextFactory;
        this.calculationContextFactory = calculationContextFactory;
    }

    public Result<?> execute(CreateOrderRequest req) {
        Order order = req.toDomain();

        //el country debe venir del customer?
        var validationContext = validationContextFactory.create("MX");
        var calculationContext = calculationContextFactory.create("MX");

        order = createOrderDomainService.execute(order,  validationContext, calculationContext);

        Order saved = orderRepository.save(order);

        List<DomainEvent> events = saved.pullDomainEvents();

        for(DomainEvent event : events) {
            eventPublisher.publish(event);
        }

        return Result.success(saved);
    }
}
