package com.alrussy_dev.inventory_service.client;

import java.util.List;
import java.util.Map;

public record SkuProduct(
        String skuCode,
        Product product,
        Map<?, ?> details,
        List<String> imageUrls,
        Double price,
        Double discount,
        String currency,
        Double priceAfterDiscount) {}
