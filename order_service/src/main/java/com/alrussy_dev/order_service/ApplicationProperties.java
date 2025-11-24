package com.alrussy_dev.order_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orders")
public record ApplicationProperties(
        String publishAllOrderEventsJobCron,
        String publishNewOrderEventJobCron,
        String productServiceUrl,
        String keyclockServerUrl,
        String topicOpenedNewCart,
        String topicAddedProductToCart,
        String topicRemovedProductFromCart,
        String topicChangedQuantity,
        String topicStartedCheckout,
        String topicAddedAddress,
        String topicAddedPaymentMethod,
        String topicCreatedOrder,
        String topicConfirmedOrder,
        String topicShippedOrder,
        String topicDeliveredOrder,
        String topicOrderCancelled,
        String topicOrderError,
        String topicEventHandler,
        String notificationOrderState) {}
