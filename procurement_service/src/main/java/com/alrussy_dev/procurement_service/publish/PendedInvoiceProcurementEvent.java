package com.alrussy_dev.procurement_service.publish;

import com.alrussy_dev.procurement_service.client.LineProductOfInventory;
import java.util.List;

public record PendedInvoiceProcurementEvent(String number, List<LineProductOfInventory> lineProducts)
        implements Event {}
