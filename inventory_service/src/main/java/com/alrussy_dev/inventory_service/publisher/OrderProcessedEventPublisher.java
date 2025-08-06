package com.alrussy_dev.inventory_service.publisher;

import com.alrussy_dev.inventory_service.model.dto.EventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProcessedEventPublisher {

    final KafkaTemplate<String, EventRequest> template;

    public void publish(EventRequest eventRequest) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
        template.send("event-handler", eventRequest);
    }
}
