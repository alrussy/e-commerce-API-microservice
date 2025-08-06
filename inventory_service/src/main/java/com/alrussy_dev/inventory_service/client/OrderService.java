package com.alrussy_dev.inventory_service.client;

import com.alrussy_dev.inventory_service.model.dto.EventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OrderService {
    private final RestClient client;

    public String processesOrder(EventRequest event) throws JsonProcessingException {
        var response = client.post()
                .uri("/add-event")
                .body(event)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
        return response;
    }
}
