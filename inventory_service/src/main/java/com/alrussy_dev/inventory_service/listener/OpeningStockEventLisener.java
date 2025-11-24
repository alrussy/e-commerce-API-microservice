package com.alrussy_dev.inventory_service.listener;

import com.alrussy_dev.inventory_service.model.dto.Event.OpeningStockEvent;
import com.alrussy_dev.inventory_service.model.dto.OpeningStock;
import com.alrussy_dev.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpeningStockEventLisener {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = {"${inventory.opening-stock-topic}"},
            groupId = "inventory-service",
            containerFactory = "kafkaListenerForOpeningStock")
    private void consumeOrder(OpeningStockEvent event) {

        log.info("successfule listener");
        inventoryService.addOpeningStock(new OpeningStock(event.skuCode(), event.openingStock()));
    }
}
