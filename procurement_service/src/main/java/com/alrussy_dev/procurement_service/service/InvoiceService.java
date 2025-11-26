package com.alrussy_dev.procurement_service.service;

import com.alrussy_dev.procurement_service.dto.Filter;
import com.alrussy_dev.procurement_service.dto.InvoiceRequest;
import com.alrussy_dev.procurement_service.dto.InvoiceResponse;
import com.alrussy_dev.procurement_service.dto.PagedResult;
import com.alrussy_dev.procurement_service.dto.ProcurementReturnRequest;

public interface InvoiceService extends BaseService<String, InvoiceResponse, InvoiceRequest> {

    InvoiceResponse findByNumber(Long number);

    PagedResult<InvoiceResponse> find(Filter invoice);

    InvoiceResponse pending(InvoiceRequest request);

    InvoiceResponse receive(Long invoiceNumber);

    InvoiceResponse cancel(ProcurementReturnRequest procurementReturn);
}
