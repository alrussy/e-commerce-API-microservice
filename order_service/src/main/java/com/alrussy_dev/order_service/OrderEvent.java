package com.alrussy_dev.order_service;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class OrderEvent {

    @Id
    private String id;

    private String aggregateId;
    private LocalDateTime timestamp;
    private String customerId;
    private String topic;
    private String event;
}
