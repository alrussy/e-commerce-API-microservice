package com.alrussy_dev.inventory_service.job;

import com.alrussy_dev.inventory_service.services.InventoryService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ActionPublishingJop {

    private final InventoryService inventoryService;

    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "scheduledTask", lockAtLeastFor = "PT30S", lockAtMostFor = "PT1M")
    public void publishAllOrderEvents() {
        log.info("Publishing Events at {}", Instant.now());
        //        inventoryService.eventPublishing();
    }
}
