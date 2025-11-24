package com.alrussy_dev.inventory_service.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ActionGroupingByDateResponse(LocalDateTime date, List<ActionForItemResponse> actions) {}
