package com.alrussy.product_service.model.dto.error_dto;

import java.time.LocalDateTime;

public record ErrorResponse(String msg, String Status, LocalDateTime date) {}
