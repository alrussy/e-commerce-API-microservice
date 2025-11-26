package com.alrussy_dev.procurement_service.dto;

public record LineProductResponse(
        String skuCode,
        Double price,
        Double discount,
        String curruncy,
        Double priceAfterDiscount,
        Integer quantity,
        Double totalLine) {}
