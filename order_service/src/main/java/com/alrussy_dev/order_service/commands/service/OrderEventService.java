package com.alrussy_dev.order_service.commands.service;

import com.alrussy_dev.order_service.commands.model.EventRequest;
import com.alrussy_dev.order_service.commands.model.OrderEvent;
import com.alrussy_dev.order_service.commands.model.OrderProcessedEvent;
import com.alrussy_dev.order_service.commands.model.enums.EventType;
import com.alrussy_dev.order_service.commands.repository.OrderEventRepository;
import com.alrussy_dev.order_service.commands.serialization.EventSerialization;
import com.alrussy_dev.order_service.commands.validator.OrderCancelledValidator;
import com.alrussy_dev.order_service.commands.validator.OrderCreatedValidator;
import com.alrussy_dev.order_service.commands.validator.OrderDeliveredValidator;
import com.alrussy_dev.order_service.commands.validator.OrderProcessedValidator;
import com.alrussy_dev.order_service.commands.validator.OrderShippedValidator;
import com.alrussy_dev.order_service.publish.OrderEventPublisher;
import com.alrussy_dev.order_service.queries.mapper.OrderMapper;
import com.alrussy_dev.order_service.queries.model.Checkout;
import com.alrussy_dev.order_service.queries.model.LineProduct;
import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.OrderRequest;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventService {

    final OrderEventRepository eventRepository;
    final OrderEventPublisher eventPublisher;
    final OrderMapper orderMapper;
    final EventSerialization eventSerialization;
    final OrderCreatedValidator createdValidator;
    final OrderProcessedValidator processedValidator;
    final OrderShippedValidator shippedValidator;
    final OrderDeliveredValidator deliveredValidator;
    final OrderCancelledValidator cancelledValidator;

    @Transactional
    public String palceOrder(OrderRequest request) {
        String orderNumber = UUID.randomUUID().toString();
        Instant createdAt = Instant.now();
        Order order = orderMapper.toEntity(request);
        order.setOrderNumber(orderNumber);
        order.setUserId("USER199");
        order.setStatus(EventType.NEW);
        order.setCreatedAt(createdAt);
        createdValidator.validate(order);
        var event = OrderEvent.builder()
                .orderNumber(orderNumber)
                .eventType(EventType.CREATED)
                .createdAt(createdAt)
                .eventId(UUID.randomUUID().toString())
                .paylod(eventSerialization.serializer(order))
                .published(false)
                .build();
        eventRepository.save(event).getId();
        eventPublisher.publish("new-order", order);
        return orderNumber;
    }

    @KafkaListener(
            topics = "${orders.topic-event-handler}",
            groupId = "order-aggregate",
            containerFactory = "kafkaListenerContainerFactory")
    public void handle(EventRequest event) {
        Instant createdAt = Instant.now();
        OrderEvent newEvent;
        if (event instanceof OrderProcessedEvent) {
            OrderProcessedEvent orderProcessedEvent = (OrderProcessedEvent) event;
            newEvent = OrderEvent.builder()
                    .eventType(EventType.PROCESSED)
                    .createdAt(createdAt)
                    .eventId(UUID.randomUUID().toString())
                    .orderNumber(orderProcessedEvent.getOrderNumber())
                    .paylod(eventSerialization.serializer(Order.builder()
                            .checkout(Checkout.builder()
                                    .lineProducts(orderProcessedEvent.getPaylod().stream()
                                            .map(t -> LineProduct.builder()
                                                    .skuCode(t.skuCode())
                                                    .quentity(t.quantity())
                                                    .build())
                                            .toList())
                                    .build())
                            .orderNumber(orderProcessedEvent.getId())
                            .build()))
                    .published(false)
                    .build();
            log.info(event.toString());
            save(newEvent);
        }
    }

    public void publishOrderEventsIsPublishedUnCommited() {
        List<OrderEvent> events =
                eventRepository.findByPublished(false, Sort.by("createdAt").ascending());
        log.info(" found {} events is not Published ", events.size());
        events.forEach(event -> {
            eventPublisher.publishEvent(event, eventSerialization);
            commitPublish(event);
        });
    }

    public void publishAllEvents() {
        List<OrderEvent> events = eventRepository.findAll(Sort.by("createdAt").ascending());
        log.info(" found {} events", events.size());
        events.forEach(event -> {
            eventPublisher.publishEvent(event, eventSerialization);
        });
    }

    public void publishEventsByOrderNumber(String orderNumber) {
        List<OrderEvent> events = eventRepository.findByOrderNumber(
                orderNumber, Sort.by("createdAt").ascending());
        log.info(" found {} events", events.size());
        events.forEach(event -> {
            eventPublisher.publishEvent(event, eventSerialization);
        });
    }

    private void commitPublish(OrderEvent event) {
        event.setPublished(true);
        eventRepository.save(event);
    }

    private String save(OrderEvent event) {
        System.out.println("Saved");
        return eventRepository.save(event).getEventId();
    }
}
