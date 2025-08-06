package com.alrussy_dev.order_service.client.product.model;

import java.util.List;

public record Category(
        Long id, String name, Boolean isFeature, String imageUrl, List<Brand> brands, List<Department> departments) {}
