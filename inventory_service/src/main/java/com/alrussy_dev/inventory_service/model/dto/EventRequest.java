package com.alrussy_dev.inventory_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class EventRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EventRequest(String eventId, String orderNumber, List<LineProductOfInventory> paylod) {
        this.eventId = eventId;
        this.orderNumber = orderNumber;
        this.paylod = paylod;
    }

    @NotBlank
    private String eventId;

    private String orderNumber;
    private List<LineProductOfInventory> paylod;
}
