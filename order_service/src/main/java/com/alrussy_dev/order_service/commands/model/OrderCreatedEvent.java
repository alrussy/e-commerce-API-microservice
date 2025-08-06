package com.alrussy_dev.order_service.commands.model;

import com.alrussy_dev.order_service.queries.model.LineProduct;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderCreatedEvent extends EventRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OrderCreatedEvent(@NotBlank String eventId, @NotBlank String orderNumber, List<LineProduct> paylod) {
        super(eventId, orderNumber);
        this.eventId = eventId;
        this.orderNumber = orderNumber;
        this.paylod = paylod.stream()
                .map(t -> new LineProductOfInventory(t.getSkuCode(), t.getQuentity()))
                .toList();
        ;
    }

    private String eventId;
    private String orderNumber;
    private List<LineProductOfInventory> paylod;
}
