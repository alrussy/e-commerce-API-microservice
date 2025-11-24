package com.alrussy_dev.order_service.queries.model.dto;

import com.alrussy_dev.order_service.common.Address;
import com.alrussy_dev.order_service.common.LineProduct;
import com.alrussy_dev.order_service.common.OrderStatus;
import com.alrussy_dev.order_service.common.PaymentMethod;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponse(
        String orderNumber,
        LocalDateTime createdAt,
        String customerId,
        OrderStatus status,
        String currency,
        Double amount,
        Address address,
        PaymentMethod paymentMethod,
        Set<LineProduct> lineProducts,
        Double taxFee,
        Double shippingFee) {}
