package com.alrussy_dev.inventory_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "inventory")
public record ApplicationProperties(
        String productServiceUrl,
        String orderServiceUrl,
        String openingStockTopic,
        String createdOrderTopic,
        String CancelledOrderTopic,
        String cancelledInvoiceTopic,
        String updatedInvoiceTopic,
        String receivedInvoiceTopic,
        String pendedInvoiceTopic) {}
