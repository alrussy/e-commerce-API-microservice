package com.alrussy_dev.order_service.queries.listener;

import com.alrussy_dev.order_service.commands.model.EventRequest;
import com.alrussy_dev.order_service.commands.model.enums.EventType;
import com.alrussy_dev.order_service.commands.service.OrderEventService;
import com.alrussy_dev.order_service.publish.OrderEventPublisher;
import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.SummaryOrder;
import com.alrussy_dev.order_service.queries.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventLisener {

    final OrderRepository repository;
    final OrderEventService eventService;
    final OrderEventPublisher eventPublisher;

    @Value(value = "${orders.notification-order-state}")
    String notifiyOrder;

    @KafkaListener(
            topics = "${orders.topic-order-new}",
            groupId = "query",
            containerFactory = "kafkaListenerContainerFactory2")
    private void createNewOrder(Order order) {
        if (order != null)
            if (!repository.existsById(order.getOrderNumber())) {
                order.setStatus(EventType.CREATED);
                repository.save(order);
            }

        eventPublisher.publish(
                notifiyOrder, new SummaryOrder(order.getOrderNumber(), order.getUserId(), order.getStatus()));
    }

    @KafkaListener(
            topics = {
                "${orders.topic-order-processed}",
            },
            groupId = "query",
            containerFactory = "kafkaListenerContainerFactory2")
    private void processedOrder(EventRequest eventRequest) {
        updateState(eventRequest, EventType.PROCESSED);
    }

    @KafkaListener(
            topics = {
                "${orders.topic-order-shipped}",
            },
            groupId = "query",
            containerFactory = "kafkaListenerContainerFactory2")
    private void shippedOrder(EventRequest eventRequest) {
        updateState(eventRequest, EventType.SHIPPED);
    }

    @KafkaListener(
            topics = {
                "${orders.topic-order-delivered}",
            },
            groupId = "query",
            containerFactory = "kafkaListenerContainerFactory2")
    private void deliveredOrder(EventRequest eventRequest) {
        updateState(eventRequest, EventType.DELIVERED);
    }

    @KafkaListener(
            topics = {
                "${orders.topic-order-cancelled}",
            },
            groupId = "query",
            containerFactory = "kafkaListenerContainerFactory2")
    private void cancelledOrder(EventRequest eventRequest) {
        updateState(eventRequest, EventType.CANCELLED);
    }

    @KafkaListener(
            topics = {
                "${orders.topic-order-error}",
            },
            groupId = "query",
            containerFactory = "kafkaListenerContainerFactory2")
    private void errorOrder(EventRequest eventRequest) {
        updateState(eventRequest, EventType.ERROR);
    }

    private void updateState(EventRequest eventRequest, EventType eventType) {
        var orderFind = repository.findById(eventRequest.getOrderNumber());

        if (orderFind.isPresent()) {
            var order = orderFind.get();
            order.setStatus(eventType);
            repository.save(order);
            eventPublisher.publish(
                    "notifiy-order", new SummaryOrder(order.getOrderNumber(), order.getUserId(), order.getStatus()));
        }
    }
}
