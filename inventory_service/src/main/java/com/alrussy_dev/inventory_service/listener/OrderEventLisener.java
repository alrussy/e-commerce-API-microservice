package com.alrussy_dev.inventory_service.listener;

import com.alrussy_dev.inventory_service.model.dto.EventRequest;
import com.alrussy_dev.inventory_service.model.enums.ActionType;
import com.alrussy_dev.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventLisener {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = {"${inventory.topic-order-created}"},
            groupId = "inventory-service",
            containerFactory = "kafkaListenerContainerFactory")
    private void consumeOrder(EventRequest request) {

        inventoryService.action(request.getOrderNumber(), ActionType.REMOVE_STOCK, request.getPaylod());
    }
}
