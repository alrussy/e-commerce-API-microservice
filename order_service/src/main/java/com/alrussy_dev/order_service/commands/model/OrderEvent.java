package com.alrussy_dev.order_service.commands.model;

import com.alrussy_dev.order_service.commands.model.enums.EventType;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "event-store")
public class OrderEvent {

    @Id
    private String id;

    private String orderNumber;

    private String eventId;

    @NotBlank
    private EventType eventType;

    private String paylod;

    @NotBlank
    private Instant createdAt;

    @NotBlank
    private String confirmBy;

    private Boolean published;

    private String details;
}
