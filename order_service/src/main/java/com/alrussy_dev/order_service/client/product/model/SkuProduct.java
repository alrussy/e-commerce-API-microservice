package com.alrussy_dev.order_service.client.product.model;

import java.util.List;

public record SkuProduct(
        String skuCode,
        Product product,
        Double price,
        Double discount,
        String currency,
        Double priceAfterDiscount,
        List<Details> details,
        List<String> imageUrls) {}
