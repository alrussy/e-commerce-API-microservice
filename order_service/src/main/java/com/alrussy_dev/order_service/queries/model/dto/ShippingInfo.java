package com.alrussy_dev.order_service.queries.model.dto;

import com.alrussy_dev.order_service.common.Address;
import java.time.LocalDateTime;

public record ShippingInfo(
        LocalDateTime shippingDate,
        LocalDateTime deliveryDate,
        String trackingNumber,
        String Courier,
        Double shippingCost,
        Address address) {}
