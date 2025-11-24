package com.alrussy_dev.order_service.queries.model.dto;

import com.alrussy_dev.order_service.common.OrderStatus;
import java.time.Instant;

public record SummaryOrder(
        String orderNumber, Instant createdDate, String customerId, OrderStatus state, Double amount) {}
