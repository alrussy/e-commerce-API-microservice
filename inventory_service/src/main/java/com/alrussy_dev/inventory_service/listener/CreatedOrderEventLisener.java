package com.alrussy_dev.inventory_service.listener;

import com.alrussy_dev.inventory_service.model.dto.ActionRequest;
import com.alrussy_dev.inventory_service.model.dto.Event.CreatedOrderEvent;
import com.alrussy_dev.inventory_service.model.enums.ActionType;
import com.alrussy_dev.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatedOrderEventLisener {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = {"${inventory.created-order-topic}"},
            groupId = "inventory-service",
            containerFactory = "kafkaListenerforOrder")
    private void createdOrder(CreatedOrderEvent event) {
        log.info(
                " Event listener... successful orderNumber : {} ,Line Products : {}",
                event.orderNumber(),
                event.lineProducts());
        inventoryService.action(
                new ActionRequest(event.orderNumber(), ActionType.REMOVE_STOCK, "Order", event.lineProducts()));
    }
}
