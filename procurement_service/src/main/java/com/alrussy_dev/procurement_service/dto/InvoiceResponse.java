package com.alrussy_dev.procurement_service.dto;

import com.alrussy_dev.procurement_service.enums.InvoiceStatus;
import com.alrussy_dev.procurement_service.enums.PaymentMethods;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceResponse(
        String id,
        Long invoiceNumber,
        String refranceNumber,
        SupplierResponse supplier,
        LocalDateTime date,
        PaymentMethods paymentMethod,
        String details,
        InvoiceStatus status,
        String currency,
        Double amount,
        List<LineProductResponse> lineProducts,
        ProcurementReturnResponse procurementReturnResponse) {}
