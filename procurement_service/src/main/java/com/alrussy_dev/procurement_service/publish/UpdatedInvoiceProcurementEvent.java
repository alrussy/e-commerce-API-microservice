package com.alrussy_dev.procurement_service.publish;

import com.alrussy_dev.procurement_service.client.LineProductOfInventory;
import java.util.List;

public record UpdatedInvoiceProcurementEvent(String number, List<LineProductOfInventory> lineProducts)
        implements Event {}
