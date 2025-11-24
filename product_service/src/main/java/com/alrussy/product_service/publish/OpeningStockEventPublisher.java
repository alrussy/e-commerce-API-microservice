package com.alrussy.product_service.publish;

import com.alrussy.product_service.model.dto.Event.OpeningStockEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

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
