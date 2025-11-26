package com.alrussy_dev.procurement_service.publish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PendedInvoiceProcurementEventPublisher {

    private final KafkaTemplate<String, PendedInvoiceProcurementEvent> template;

    void publish(String topic, PendedInvoiceProcurementEvent event) {
        log.info("Publish Event :{}", event);
        template.send(topic, event);
    }
}
