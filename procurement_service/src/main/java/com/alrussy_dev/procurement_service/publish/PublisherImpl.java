package com.alrussy_dev.procurement_service.publish;

import com.alrussy_dev.procurement_service.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary
@Slf4j
@RequiredArgsConstructor
public class PublisherImpl implements Publisher {

    @Autowired
    ApplicationProperties applicationProperties;

    private final KafkaTemplate<String, Event> template;

    @Override
    public void publish(Event event) {

        log.info(applicationProperties.updatedInvoiceTopic());
        switch (event) {
            case CancelledInvoiceProcurementEvent e -> template.send(applicationProperties.cancelledInvoiceTopic(), e);
            case UpdatedInvoiceProcurementEvent e -> template.send(applicationProperties.updatedInvoiceTopic(), e);
            case PendedInvoiceProcurementEvent e -> template.send(applicationProperties.pendedInvoiceTopic(), e);
            case ReceivedInvoiceProcurementEvent e -> template.send(applicationProperties.receivedInvoiceTopic(), e);
            default -> throw new IllegalArgumentException("Unexpected value: " + event);
        }
    }
}
