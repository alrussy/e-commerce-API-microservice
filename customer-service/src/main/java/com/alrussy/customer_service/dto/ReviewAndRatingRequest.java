package com.alrussy.customer_service.dto;

public record ReviewAndRatingRequest(String userId, String skuCode, String review, Double rating) {}
