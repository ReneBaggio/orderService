package com.orderservice.application;

import com.orderservice.domain.events.DomainEvent;
import com.orderservice.domain.model.Order;
import com.orderservice.domain.ports.EventPublisher;
import com.orderservice.domain.ports.OrderRepository;
import com.orderservice.domain.services.CreateOrderDomainService;
import com.orderservice.infrastructure.web.dto.CreateOrderRequest;
import com.orderservice.shared.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateOrderApplicationService {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;
    private final CreateOrderDomainService createOrderDomainService;

    public CreateOrderApplicationService(OrderRepository orderRepository, EventPublisher eventPublisher, CreateOrderDomainService createOrderDomainService) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
        this.createOrderDomainService = createOrderDomainService;
    }

    public Result<?> execute(CreateOrderRequest req) {
        Order order = req.toDomain();

        if(createOrderDomainService.validate(order)) {
            return Result.businessError(createOrderDomainService.getBusinessErrors());
        }

        Order saved = orderRepository.save(order);

        List<DomainEvent> events = saved.pullDomainEvents();

        for(DomainEvent event : events) {
            eventPublisher.publish(event);
        }

        return Result.success(saved);
    }
}
