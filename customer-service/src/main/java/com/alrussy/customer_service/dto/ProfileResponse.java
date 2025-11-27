package com.alrussy.customer_service.dto;

import java.time.LocalDateTime;

public record ProfileResponse(
        String firstname, String lastname, LocalDateTime dateOfBrith, Gender gender, String imageProfile) {}
