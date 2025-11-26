package com.alrussy_dev.procurement_service.mapper;

import com.alrussy_dev.procurement_service.dto.InvoiceRequest;
import com.alrussy_dev.procurement_service.dto.InvoiceResponse;
import com.alrussy_dev.procurement_service.dto.ProcurementReturnRequest;
import com.alrussy_dev.procurement_service.entity.Invoice;

public interface InvoiceMapper extends BaseMapper<Invoice, InvoiceResponse, InvoiceRequest> {

    Invoice addReturn(Invoice invoice, ProcurementReturnRequest request);
}
