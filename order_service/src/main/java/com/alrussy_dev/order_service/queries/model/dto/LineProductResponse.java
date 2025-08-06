package com.alrussy_dev.order_service.queries.model.dto;

public record LineProductResponse(
        String skuCode,
        Double price,
        Double discount,
        String curruncy,
        Double priceAfterDiscount,
        Integer quentity,
        Double lineTotal) {}
