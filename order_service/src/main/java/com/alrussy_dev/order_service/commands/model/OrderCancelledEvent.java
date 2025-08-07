package com.alrussy_dev.order_service.commands.model;

import com.alrussy_dev.order_service.queries.model.Order;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCancelledEvent extends EventRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3L;

    public OrderCancelledEvent(String eventId, String orderNumber, Order paylod, String reason) {

        super(eventId, orderNumber);

        this.paylod = paylod;
        this.reason = reason;
    }

    private Order paylod;

    private String reason;
}
