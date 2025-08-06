package com.alrussy_dev.order_service.queries.model;

import com.alrussy_dev.order_service.commands.model.enums.EventType;

public record OrderEventRequest(String orderNumber, EventType type) {}
