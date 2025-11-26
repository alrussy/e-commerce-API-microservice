package com.alrussy_dev.procurement_service.publish;

public record ReceivedInvoiceProcurementEvent(String number) implements Event {}
