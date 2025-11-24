package com.alrussy.product_service.publish;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.alrussy.product_service.model.dto.Event.OpeningStockEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpeningStockEventPublisher {

    final KafkaTemplate<String, OpeningStockEvent> template;

    public void publish(String topic, OpeningStockEvent event) {
        log.info("Publish Event :{}", event);
        template.send(topic, event);
    }
}
