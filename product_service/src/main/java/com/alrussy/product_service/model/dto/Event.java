package com.alrussy.product_service.model.dto;

import java.io.Serializable;

public sealed interface Event extends Serializable {

    /**
     *
     */
    public record OpeningStockEvent(String skuCode, Integer openingStock) implements Event {
        private static final long serialVersionUID = 1L;
    }
}
