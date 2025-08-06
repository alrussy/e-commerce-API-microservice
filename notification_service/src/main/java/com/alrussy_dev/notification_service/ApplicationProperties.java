package com.alrussy_dev.notification_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "notifications")
public record ApplicationProperties(String supportEmail, String topicOrderState) {}
