package com.alrussy_dev.procurement_service.dto;

import com.alrussy_dev.procurement_service.enums.InvoiceStatus;
import com.alrussy_dev.procurement_service.enums.PaymentMethods;
import java.time.LocalDateTime;

public record Filter(
        Integer pageNumber,
        String[] sortedby,
        String direction,
        Long invoiceNumber,
        String supplierId,
        InvoiceStatus status,
        LocalDateTime fromDate,
        LocalDateTime toDate,
        PaymentMethods paymentMethod,
        Double amountGreateThan,
        Double amountGreateThanOrEqual,
        Double amountLessThanOrEqual,
        Double amountLessThan,
        Double amountEqual,
        String skuCode) {}
