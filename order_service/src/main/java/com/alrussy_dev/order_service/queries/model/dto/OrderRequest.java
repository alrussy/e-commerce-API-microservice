package com.alrussy_dev.order_service.queries.model.dto;

import jakarta.validation.Valid;

public record OrderRequest(@Valid CheckoutRequest checkout) {}
