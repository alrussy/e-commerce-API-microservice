package com.alrussy_dev.order_service.queries.model;

import com.alrussy_dev.order_service.commands.model.enums.EventType;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "orders")
public class Order {

    @Id
    private String orderNumber;

    @NotBlank
    private String userId;

    @NotBlank
    private Instant createdAt;

    @NotBlank
    private EventType status;
    
    private Checkout checkout;
}
