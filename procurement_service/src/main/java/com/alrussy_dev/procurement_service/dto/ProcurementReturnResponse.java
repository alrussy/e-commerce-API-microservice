package com.alrussy_dev.procurement_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProcurementReturnResponse(
        String id,
        Long returnNumber,
        LocalDateTime date,
        String details,
        String currency,
        Double amount,
        List<LineProductResponse> lineProducts) {}
