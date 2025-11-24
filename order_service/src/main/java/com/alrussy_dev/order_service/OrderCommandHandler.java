package com.alrussy_dev.order_service;

import com.alrussy_dev.order_service.Event.AddedAddressEvent;
import com.alrussy_dev.order_service.Event.AddedPaymentMethodEvent;
import com.alrussy_dev.order_service.Event.AddedProductEvent;
import com.alrussy_dev.order_service.Event.ChangedQuantityEvent;
import com.alrussy_dev.order_service.Event.ConfirmedOrderEvent;
import com.alrussy_dev.order_service.Event.CreatedOrderEvent;
import com.alrussy_dev.order_service.Event.DeliveredOrderEven;
import com.alrussy_dev.order_service.Event.OpenedNewCartEvent;
import com.alrussy_dev.order_service.Event.RemovedProductEvent;
import com.alrussy_dev.order_service.Event.ShippedOrderEvent;
import com.alrussy_dev.order_service.Event.StartedCheckoutEvent;
import com.alrussy_dev.order_service.common.OrderPublisher;
import com.alrussy_dev.order_service.error.CommandException;
import com.alrussy_dev.order_service.publish.OrderEventPublisher;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCommandHandler {

    private final EventSerialization eventSerialization;
    private final EventStore eventStore;
    private final OrderEventPublisher eventPublisher;

    public Boolean handle(OrderCommands commands) throws CommandException {
        OrderAggregate order = null;
        switch (commands) {
            case OrderCommands.OpenNewCartCommand command -> {
                String aggregateId = UUID.randomUUID().toString();
                List<Event> events = getEvents(loadEventByCustomerId(command.customerId()));
                order = new OrderAggregate(aggregateId, events);
                order.openNewCart(command.customerId());
            }
            case OrderCommands.AddProductCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                System.out.println(events.toString());
                order = new OrderAggregate(command.aggregateId(), events);
                order.addProduct(command.lineProduct());
            }
            case OrderCommands.RemoveProductCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.removeProduct(command.skuCode());
            }
            case OrderCommands.ChangeQuantityCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.changeQuantity(command.quantity(), command.skuCode());
            }
            case OrderCommands.StartCheckoutCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));

                order = new OrderAggregate(command.aggregateId(), events);
                order.startCheckout();
            }
            case OrderCommands.AddAddressCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.addAddress(command.address());
            }
            case OrderCommands.AddPaymentMethodCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.addPaymentMethod(command.paymentMethod());
            }
            case OrderCommands.AddShippingFeeAndTaxFeeCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.addPaymentMethod(command.shippingFee(), command.taxFee());
            }
            case OrderCommands.CreateOrderCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.createOrder();
            }
            case OrderCommands.ConfirmOrderCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.confirmOrder();
            }
            case OrderCommands.ShipOrderCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.shipOrder();
            }
            case OrderCommands.DeliveryOrderCommand command -> {
                List<Event> events = getEvents(loadEventForAggreget(command.aggregateId()));
                order = new OrderAggregate(command.aggregateId(), events);
                order.deliveryOrder();
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + commands);
        }

        List<Event> newEvents = order.getUncommittedEvents();
        eventStore.save(OrderEvent.builder()
                .aggregateId(order.getAggregateId())
                .customerId(order.getCustomerId())
                .timestamp(newEvents.getFirst().timestamp())
                .event(eventSerialization.serializer(newEvents.getFirst()))
                .topic(newEvents.getFirst().topic())
                .build());
        eventPublisher.publish(order.getTopic(), newEvents.getFirst());
        return true;
    }

    private List<OrderEvent> loadEventForAggreget(String aggregateId) {
        return eventStore.findByAggregateId(aggregateId, Sort.by("timestamp").ascending());
    }

    private List<OrderEvent> loadEventByCustomerId(String customerId) {
        return eventStore.findByCustomerId(customerId, Sort.by("timestamp").ascending());
    }

    public void rebuildAllEvents() {
        List<OrderEvent> events = eventStore.findAll(Sort.by("timestamp").ascending());
        log.info(" found {} events", events.size());
        events.stream().collect(Collectors.groupingBy(e -> e.getAggregateId())).forEach((key, values) -> {
            log.info(" found by orderNumber {}  {} events", key, values.size());
            getEvents(values).stream().forEach(event -> {
                OrderAggregate aggregate = new OrderAggregate(key, List.of());
                aggregate.apply(event);
                OrderPublisher orderPublisher = new OrderPublisher(
                        aggregate.getAggregateId(),
                        aggregate.getCustomerId(),
                        aggregate.getTimestamp(),
                        aggregate.getCheckoutStatus(),
                        aggregate.getCartStatus(),
                        aggregate.getOrderStatus(),
                        aggregate.getAddress(),
                        aggregate.getPaymentMethod(),
                        aggregate.getLineProducts(),
                        aggregate.getTopic());
                eventPublisher.publish(aggregate.getTopic(), event);
            });
        });
    }

    private List<Event> getEvents(List<OrderEvent> eventRequest) {

        List<Event> events = new ArrayList<Event>();
        eventRequest.forEach(event -> {
            if (event.getTopic().equals("Opened-New-Card")) {
                events.add(eventSerialization.deserializer(event.getEvent(), OpenedNewCartEvent.class));
            }
            if (event.getTopic().equals("Added-Product-To-Cart")) {
                events.add(eventSerialization.deserializer(event.getEvent(), AddedProductEvent.class));
            }
            if (event.getTopic().equals("Removed-Product-From-Cart")) {
                events.add(eventSerialization.deserializer(event.getEvent(), RemovedProductEvent.class));
            }
            if (event.getTopic().equals("Changed-Quantity")) {
                events.add(eventSerialization.deserializer(event.getEvent(), ChangedQuantityEvent.class));
            }
            if (event.getTopic().equals("Started-Checkout")) {
                events.add(eventSerialization.deserializer(event.getEvent(), StartedCheckoutEvent.class));
            }
            if (event.getTopic().equals("Added-Address")) {
                events.add(eventSerialization.deserializer(event.getEvent(), AddedAddressEvent.class));
            }
            if (event.getTopic().equals("Added-Payment-Method")) {
                events.add(eventSerialization.deserializer(event.getEvent(), AddedPaymentMethodEvent.class));
            }
            if (event.getTopic().equals("Created-Order")) {
                events.add(eventSerialization.deserializer(event.getEvent(), CreatedOrderEvent.class));
            }

            if (event.getTopic().equals("Confirmed-Order")) {
                events.add(eventSerialization.deserializer(event.getEvent(), ConfirmedOrderEvent.class));
            }
            if (event.getTopic().equals("Shipped-Order")) {
                events.add(eventSerialization.deserializer(event.getEvent(), ShippedOrderEvent.class));
            }
            if (event.getTopic().equals("Delivered-Order")) {
                events.add(eventSerialization.deserializer(event.getEvent(), DeliveredOrderEven.class));
            }
        });
        return events;
    }
    ;
}
