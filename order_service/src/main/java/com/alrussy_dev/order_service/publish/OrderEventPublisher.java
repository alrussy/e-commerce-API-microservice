package com.alrussy_dev.order_service.publish;

import com.alrussy_dev.order_service.commands.model.EventRequest;
import com.alrussy_dev.order_service.commands.model.LineProductOfInventory;
import com.alrussy_dev.order_service.commands.model.OrderCancelledEvent;
import com.alrussy_dev.order_service.commands.model.OrderCreatedEvent;
import com.alrussy_dev.order_service.commands.model.OrderDeliveredEvent;
import com.alrussy_dev.order_service.commands.model.OrderErrorEvent;
import com.alrussy_dev.order_service.commands.model.OrderEvent;
import com.alrussy_dev.order_service.commands.model.OrderProcessedEvent;
import com.alrussy_dev.order_service.commands.model.OrderShippedEvent;
import com.alrussy_dev.order_service.commands.model.enums.EventType;
import com.alrussy_dev.order_service.commands.serialization.EventSerialization;
import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.SummaryOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventPublisher {

    final KafkaTemplate<String, Object> template;
    // final KafkaTemplate<String, OrderEventRequest> template2 ;

    public void publish(String topic, EventRequest event) {
        log.info("publish order event :{}", event);
        template.send(topic, event);
    }

    public void publish(String topic, SummaryOrder order) {
        log.info("publish new order :{}", order.toString());
        template.send(topic, order);
    }

    public void publish(String topic, Order order) {
        log.info("publish new order :{}", order.toString());
        template.send(topic, order);
    }

    public void publishEvent(OrderEvent event, EventSerialization eventSerialization) {
        EventType eventType = event.getEventType();
        String topic;
        if (!event.getPublished()) {
            EventRequest eventRequest;

            switch (eventType) {
                case CREATED: {
                    topic = "create-order";
                    Order order = eventSerialization.deserializer(event.getPaylod(), Order.class);
                    eventRequest = new OrderCreatedEvent(
                            event.getEventId(),
                            event.getOrderNumber(),
                            order.getCheckout().getLineProducts());
                    break;
                }

                case PROCESSED: {
                    topic = "process-order";
                    var order = eventSerialization.deserializer(event.getPaylod(), Order.class);
                    eventRequest = new OrderProcessedEvent(
                            event.getEventId(),
                            event.getOrderNumber(),
                            order.getCheckout().getLineProducts().stream()
                                    .map(t -> new LineProductOfInventory(t.getSkuCode(), t.getQuentity()))
                                    .toList());
                    break;
                }

                case SHIPPED: {
                    topic = "shipped-order";
                    eventRequest = new OrderShippedEvent();
                    break;
                }

                case DELIVERED: {
                    topic = "delivered-order";
                    eventRequest = new OrderDeliveredEvent();
                    break;
                }

                case CANCELLED: {
                    topic = "cancelled-order";
                    eventRequest = new OrderCancelledEvent();
                    break;
                }

                case ERROR: {
                    topic = "error";
                    eventRequest = new OrderErrorEvent();
                    break;
                }

                default:
                    throw new RuntimeException("UnSupported Order Event Type: " + eventType);
            }
            this.publish(topic, eventRequest);
        }
    }
}
