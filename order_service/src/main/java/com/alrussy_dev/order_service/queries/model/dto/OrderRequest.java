package com.alrussy_dev.order_service.queries.model.dto;

import com.alrussy_dev.order_service.common.Address;
import com.alrussy_dev.order_service.common.CartStatus;
import com.alrussy_dev.order_service.common.CheckoutStatus;
import com.alrussy_dev.order_service.common.LineProduct;
import com.alrussy_dev.order_service.common.OrderStatus;
import com.alrussy_dev.order_service.common.PaymentMethod;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderRequest(
        String orderNumber,
        String customerId,
        LocalDateTime createdAt,
        CheckoutStatus checkoutStatus,
        CartStatus cartStatues,
        OrderStatus orderStatus,
        Address address,
        PaymentMethod paymentMethod,
        Set<LineProduct> lineProduts,
        Double amount,
        String topic) {}
