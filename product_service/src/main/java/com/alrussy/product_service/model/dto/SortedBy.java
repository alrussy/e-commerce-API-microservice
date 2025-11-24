package com.alrussy.product_service.model.dto;

import java.io.Serializable;

public record SortedBy(String fieldName, String direction) implements Serializable {}
