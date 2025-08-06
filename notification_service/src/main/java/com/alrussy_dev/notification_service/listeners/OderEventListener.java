package com.alrussy_dev.notification_service.listeners;

import com.alrussy_dev.notification_service.ApplicationProperties;
import com.alrussy_dev.notification_service.entity.SummaryOrder;
import com.alrussy_dev.notification_service.service.NotifiOrderStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OderEventListener {

    private final NotifiOrderStateService notifiOrderStateService;
    private final ApplicationProperties properties;

    @KafkaListener(
            topics = "notifiy-order",
            groupId = "notification-order",
            containerFactory = "kafkaListenerContainerFactory")
    private void orderCreated(SummaryOrder order) {
        notifiOrderStateService.sendAndSaveOrderState(order);
    }
}
