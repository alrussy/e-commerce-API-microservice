package com.alrussy_dev.order_service.commands.model;

import com.alrussy_dev.order_service.commands.model.enums.EventType;
import com.alrussy_dev.order_service.queries.model.LineProduct;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderShippedEvent extends EventRequest {

    @NotBlank
    private String orderNumber;

    @NotBlank
    private EventType type;

    @Getter
    private List<LineProduct> items;
}
