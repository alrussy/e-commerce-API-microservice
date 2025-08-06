package com.alrussy_dev.order_service.client.product.model;

public record Product(
        Long id,
        String name,
        Boolean isFeature,
        String imageUrl,
        Category category,
        Department department,
        Brand brand,
        String about) {}
