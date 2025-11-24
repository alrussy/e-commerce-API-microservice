package com.alrussy_dev.inventory_service.listener;

import com.alrussy_dev.inventory_service.model.dto.ActionRequest;
import com.alrussy_dev.inventory_service.model.dto.Event.ReceivedInvoiceProcurementEvent;
import com.alrussy_dev.inventory_service.model.enums.ActionType;
import com.alrussy_dev.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReceivedProcurementEventLisener {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = {"${inventory.received-invoice-topic}"},
            groupId = "inventory-service")
    private void orderCreated(ReceivedInvoiceProcurementEvent event) {
        log.info(" Event listener... successful orderNumber : {} ,Line Products : {}", event.number());
        inventoryService.action(new ActionRequest(event.number(), ActionType.PROCESS, "", null));
    }
}
