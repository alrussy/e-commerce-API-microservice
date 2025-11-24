package com.alrussy_dev.order_service.queries.listener;

import com.alrussy_dev.order_service.Event.AddedAddressEvent;
import com.alrussy_dev.order_service.Event.AddedPaymentMethodEvent;
import com.alrussy_dev.order_service.Event.AddedProductEvent;
import com.alrussy_dev.order_service.Event.ChangedQuantityEvent;
import com.alrussy_dev.order_service.Event.CreatedOrderEvent;
import com.alrussy_dev.order_service.Event.OpenedNewCartEvent;
import com.alrussy_dev.order_service.Event.RemovedProductEvent;
import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventLisener {

    final OrderRepository repository;

    @Value(value = "${orders.notification-order-state}")
    String notifiyOrder;

    @KafkaListener(
            topics = {"${orders.topic-opened-new-cart}"},
            groupId = "query-order",
            containerFactory = "kafkaListenerContainerFactory2")
    public void openNewCart(OpenedNewCartEvent event) {
        repository.save(Order.builder()
                .orderNumber(event.aggregateId())
                .customerId(event.customerId())
                .build());
    }

    @KafkaListener(
            topics = {"${orders.topic-added-product-to-cart}"},
            groupId = "query-order",
            containerFactory = "kafkaListenerContainerFactory2")
    public void addLineProduct(AddedProductEvent event) {
        Order order = repository.findById(event.aggregateId()).orElse(null);
        order.addLineProduct(event.lineProduct());
        repository.save(order);
    }

    @KafkaListener(
            topics = {"${orders.topic-removed-product-from-cart}"},
            groupId = "query-order",
            containerFactory = "kafkaListenerContainerFactory2")
    public void removeLineProduct(RemovedProductEvent event) {

        Order order = repository.findById(event.aggregateId()).orElse(null);
        if (order != null && event.skuCode() != null) {
            order.removeLineProduct(order.getLineProducts().stream()
                    .filter(line -> line.getSkuCode().equals(event.skuCode()))
                    .findFirst()
                    .get());
            repository.save(order);
        }
    }

    @KafkaListener(
            topics = {"${orders.topic-changed-quantity}"},
            groupId = "query-order",
            containerFactory = "kafkaListenerContainerFactory2")
    public void changeQuantity(ChangedQuantityEvent event) {

        Order order = repository.findById(event.aggregateId()).orElse(null);

        if (order != null) {
            var lineProduct = order.getLineProducts().stream()
                    .filter(line -> line.getSkuCode().equals(event.skuCode()))
                    .findFirst()
                    .get();
            lineProduct.setQuantity(event.quantity());
            repository.save(order);
        }
    }

    @KafkaListener(
            topics = "${orders.topic-created-order}",
            groupId = "query-order",
            containerFactory = "kafkaListenerContainerFactory2")
    public void createOrder(CreatedOrderEvent event) {
        System.out.println("============");
        Order order = repository.findById(event.aggregateId()).orElse(null);

        if (order != null) {

            order.setCreatedAt(event.timestamp());
            order.setStatus(event.orderStatus());
            order.setAmount(order.getLineProducts().stream()
                    .mapToDouble(line ->
                            (line.getPrice() - (line.getPrice() * line.getDiscount() / 100)) * line.getQuantity())
                    .sum());

            repository.save(order);
        }
    }

    @KafkaListener(
            topics = {"${orders.topic-added-address}"},
            groupId = "query-order",
            containerFactory = "kafkaListenerContainerFactory2")
    public void setAddress(AddedAddressEvent event) {
        Order order = repository
                .findById(event.aggregateId())
                .orElseThrow(
                        () -> new RuntimeException("The Checkout By This ID : " + event.aggregateId() + " Not Found"));
        order.setAddress(event.address());
        repository.save(order);
    }

    @KafkaListener(
            topics = {"${orders.topic-added-payment-method}"},
            groupId = "query-order",
            containerFactory = "kafkaListenerContainerFactory2")
    public void setPaymentMethod(AddedPaymentMethodEvent event) {
        Order order = repository
                .findById(event.aggregateId())
                .orElseThrow(
                        () -> new RuntimeException("The Checkout By This ID : " + event.aggregateId() + " Not Found"));
        order.setPaymentMethod(event.paymentMethod());
        repository.save(order);
    }
}
//    @KafkaListener(
//            topics = {
//                "${orders.topic-order-processed}",
//            },
//            groupId = "query",
//            containerFactory = "kafkaListenerContainerFactory2")
//    private void processedOrder(EventRequest eventRequest) {
//        updateState(eventRequest, EventType.PROCESSED);
//    }
//
//    @KafkaListener(
//            topics = {
//                "${orders.topic-order-shipped}",
//            },
//            groupId = "query",
//            containerFactory = "kafkaListenerContainerFactory2")
//    private void shippedOrder(EventRequest eventRequest) {
//        updateState(eventRequest, EventType.SHIPPED);
//    }
//
//    @KafkaListener(
//            topics = {
//                "${orders.topic-order-delivered}",
//            },
//            groupId = "query",
//            containerFactory = "kafkaListenerContainerFactory2")
//    private void deliveredOrder(EventRequest eventRequest) {
//        updateState(eventRequest, EventType.DELIVERED);
//    }
//
//    @KafkaListener(
//            topics = {
//                "${orders.topic-order-cancelled}",
//            },
//            groupId = "query",
//            containerFactory = "kafkaListenerContainerFactory2")
//    private void cancelledOrder(EventRequest eventRequest) {
//        updateState(eventRequest, EventType.CANCELLED);
//    }
//
//    @KafkaListener(
//            topics = {
//                "${orders.topic-order-error}",
//            },
//            groupId = "query",
//            containerFactory = "kafkaListenerContainerFactory2")
//    private void errorOrder(EventRequest eventRequest) {
//        updateState(eventRequest, EventType.ERROR);
//    }
//
//    private void updateState(EventRequest eventRequest, EventType eventType) {
//        var orderFind = repository.findById(eventRequest.getOrderNumber());
//
//        if (orderFind.isPresent()) {
//            var order = orderFind.get();
//            repository.save(order);
//            eventPublisher.publish(
//                    "notifiy-order", new SummaryOrder(order.getOrderNumber(),order.getCreatedAt(), order.getUserId(),
// order.getStatus(),null));
//        }
//    }
// }
