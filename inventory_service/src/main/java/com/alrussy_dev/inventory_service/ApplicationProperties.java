package com.alrussy_dev.inventory_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "inventory")
public record ApplicationProperties(
        String orderServiceUrl, String topicOrderEventHandelr, String topicOrderCreated, String topicOrderCancelled) {}
