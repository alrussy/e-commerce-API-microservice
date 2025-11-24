package com.alrussy_dev.order_service.queries.model.dto;

import com.alrussy_dev.order_service.common.OrderStatus;
import com.alrussy_dev.order_service.common.PaymentMethod;
import java.time.LocalDateTime;

public record Filter(
        Integer pageNumber,
        String[] sortedby,
        String direction,
        Long orderNumber,
        String customerId,
        OrderStatus status,
        LocalDateTime fromDate,
        LocalDateTime toDate,
        PaymentMethod paymentMethod,
        Double amountGreateThan,
        Double amountGreateThanOrEqual,
        Double amountLessThanOrEqual,
        Double amountLessThan,
        Double amountEqual,
        String skuCode) {}
