package com.alrussy_dev.order_service.publish;

import com.alrussy_dev.order_service.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventPublisher {

    final KafkaTemplate<String, Event> template;

    public void publish(String topic, Event event) {
        template.send(topic, event);
    }
}
