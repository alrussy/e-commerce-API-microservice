package com.alrussy_dev.procurement_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "procurement")
public record ApplicationProperties(
        String inventoryServiceUrl,
        String cancelledInvoiceTopic,
        String updatedInvoiceTopic,
        String receivedInvoiceTopic,
        String pendedInvoiceTopic) {}
