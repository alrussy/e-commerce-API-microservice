package com.alrussy_dev.order_service.queries.model.dto;

import java.util.List;

public record CheckoutResponse(
        Long addressId,
        Double discount,
        Double shippingFee,
        Double taxFee,
        String paymentMethod,
        String deliveryType,
        Double subTotal,
        Double orderTotal,
        List<LineProductResponse> lineProducts) {}
