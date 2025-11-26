package com.alrussy_dev.procurement_service.dto;

import com.alrussy_dev.procurement_service.enums.InvoiceStatus;
import com.alrussy_dev.procurement_service.enums.PaymentMethods;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceRequest(
        String supplierId,
        LocalDateTime date,
        PaymentMethods paymentMethod,
        String refranceNumber,
        String currency,
        String details,
        InvoiceStatus status,
        ProcurementReturnRequest returnRequest,
        List<LineProductRequest> lineProducts) {}
