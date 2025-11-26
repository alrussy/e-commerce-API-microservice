package com.alrussy_dev.procurement_service.publish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReceivedInvoiceProcurementEventPublisher {

    private final KafkaTemplate<String, ReceivedInvoiceProcurementEvent> template;

    void publish(String topic, ReceivedInvoiceProcurementEvent event) {
        log.info("Publish Event :{}", event);
        template.send(topic, event);
    }
}
