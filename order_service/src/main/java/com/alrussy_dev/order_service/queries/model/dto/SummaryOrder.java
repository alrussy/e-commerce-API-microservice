package com.alrussy_dev.order_service.queries.model.dto;

import com.alrussy_dev.order_service.commands.model.enums.EventType;

public record SummaryOrder(String orderNumber, String userId, EventType state) {}
