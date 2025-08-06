package com.alrussy_dev.order_service.jobs;

import com.alrussy_dev.order_service.commands.service.OrderEventService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublishingJob {
    final OrderEventService eventService;

    @Scheduled(cron = "${orders.publish-all-order-events-job-cron}")
    @SchedulerLock(name = "scheduledTask", lockAtLeastFor = "PT30S", lockAtMostFor = "PT1M")
    void publishAllOrderEvents() {
        log.info("Publishing Order Events at {}", Instant.now());
        eventService.publishOrderEventsIsPublishedUnCommited();
    }
}
