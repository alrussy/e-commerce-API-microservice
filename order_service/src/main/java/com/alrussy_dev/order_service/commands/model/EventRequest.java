package com.alrussy_dev.order_service.commands.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class EventRequest {

    String eventId;
    String orderNumber;
}
