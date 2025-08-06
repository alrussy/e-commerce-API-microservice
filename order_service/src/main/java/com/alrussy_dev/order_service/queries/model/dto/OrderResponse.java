package com.alrussy_dev.order_service.queries.model.dto;

import com.alrussy_dev.order_service.commands.model.enums.EventType;
import java.time.Instant;

public record OrderResponse(
        String orderNumber, Instant orderDate, String userId, EventType status, CheckoutResponse checkout) {}
