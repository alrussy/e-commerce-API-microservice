package com.alrussy.customer_service.dto;

import java.time.Instant;

public record ReviewAndRatingResponse(
        String userId,
        String skuCode,
        String customerFullname,
        String customerImageUrl,
        String review,
        double rating,
        Instant dateReview) {}
