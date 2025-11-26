package com.alrussy_dev.procurement_service.dto;

import java.time.LocalDateTime;

public record ProcurementReturnRequest(Long invoiceNumber, LocalDateTime date, String details) {}
