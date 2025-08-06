package com.alrussy_dev.order_service.client.product.model;

public record Department(
        Long id, String name, Category category, Boolean isFeature, String imageUrl, Product product) {}
