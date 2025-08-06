package com.alrussy_dev.order_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orders")
public record ApplicationProperties(
        String publishAllOrderEventsJobCron,
        String publishNewOrderEventJobCron,
        String productServiceUrl,
        String topicOrderNew,
        String topicOrderCreated,
        String topicOrderProcessed,
        String topicOrderShipped,
        String topicOrderDelivered,
        String topicOrderCancelled,
        String topicOrderError,
        String topicEventHandler,
        String notificationOrderState) {}
