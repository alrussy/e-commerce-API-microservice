package com.alrussy_dev.order_service.queries.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.NonNull;

public record CheckoutRequest(
        @NonNull Long addressId,
        @Min(0) Double discount,
        @Min(0) Double shippingFee,
        @Min(0) Double taxFee,
        @NotBlank String paymentMethod,
        @NotBlank String deliveryType,
        @Valid List<LineProductRequest> lineProducts) {}
