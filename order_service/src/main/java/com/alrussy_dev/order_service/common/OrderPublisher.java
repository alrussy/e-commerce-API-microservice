package com.alrussy_dev.order_service.common;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderPublisher(
        String orderNumber,
        String customerId,
        LocalDateTime createdAt,
        CheckoutStatus checkoutStatus,
        CartStatus cartStatues,
        OrderStatus orderStatus,
        Address address,
        PaymentMethod paymentMethod,
        Set<LineProduct> lineProduts,
        String topic) {}
