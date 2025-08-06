package com.alrussy_dev.order_service.client.product.model;

import java.util.List;

public record Brand(
        Long id, String name, String imageUrl, Boolean isFeature, Integer productCount, List<Category> categories) {}
