package com.alrussy_dev.order_service.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(int statusCode, Info info) {

    record Info(
            @JsonProperty("massage") String massage,
            String reason,
            @JsonProperty("status code") int statusCode,
            String timestamp,
            String uri) {}
}
