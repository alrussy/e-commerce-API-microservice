package com.alrussy_dev.inventory_service.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.io.Serializable;
import java.util.List;

public sealed interface Event extends Serializable {

    public record CreatedOrderEvent(
            @JsonAlias("aggregateId") String orderNumber, List<LineProductOfInventory> lineProducts) implements Event {}

    public record CancelledOrderEvent(
            @JsonAlias("aggregateId") String orderNumber, List<LineProductOfInventory> lineProducts) implements Event {}

    public record OpeningStockEvent(String skuCode, Integer openingStock) implements Event {}

    public record CancelledInvoiceProcurementEvent(String number, List<LineProductOfInventory> lineProducts)
            implements Event {}

    public record PendedInvoiceProcurementEvent(String number, List<LineProductOfInventory> lineProducts)
            implements Event {}

    public record ReceivedInvoiceProcurementEvent(String number) implements Event {}

    public record UpdatedInvoiceProcurementEvent(String number, List<LineProductOfInventory> lineProducts)
            implements Event {}
}
