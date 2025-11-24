package com.alrussy_dev.inventory_service.listener;

import com.alrussy_dev.inventory_service.model.dto.ActionRequest;
import com.alrussy_dev.inventory_service.model.dto.Event.CancelledOrderEvent;
import com.alrussy_dev.inventory_service.model.enums.ActionType;
import com.alrussy_dev.inventory_service.services.InventoryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelledOrderEventLisener {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = {"${inventory.cancelled-invoice-topic}"},
            groupId = "inventory-service")
    private void createdOrder(CancelledOrderEvent event) {
        log.info(
                " Event listener... successful orderNumber : {} ,Line Products : {}",
                event.orderNumber(),
                event.lineProducts());
        inventoryService.action(new ActionRequest(
                UUID.randomUUID().toString(), ActionType.ADD_STOCK, "Return Order", event.lineProducts()));
    }
}
