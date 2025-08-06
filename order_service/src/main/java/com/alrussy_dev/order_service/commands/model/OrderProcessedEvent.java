package com.alrussy_dev.order_service.commands.model;

import groovy.transform.builder.Builder;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class OrderProcessedEvent extends EventRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OrderProcessedEvent(String eventId, String orderNumber, List<LineProductOfInventory> paylod) {
        super(eventId, orderNumber);
        this.eventId = eventId;
        this.orderNumber = orderNumber;
        this.paylod = paylod;
    }

    @NotBlank
    private String id;

    private String orderNumber;
    private List<LineProductOfInventory> paylod;
}
