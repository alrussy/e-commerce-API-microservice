package com.alrussy_dev.procurement_service.controller;

import com.alrussy_dev.procurement_service.dto.Filter;
import com.alrussy_dev.procurement_service.dto.InvoiceRequest;
import com.alrussy_dev.procurement_service.dto.InvoiceResponse;
import com.alrussy_dev.procurement_service.dto.PagedResult;
import com.alrussy_dev.procurement_service.dto.ProcurementReturnRequest;
import com.alrussy_dev.procurement_service.enums.InvoiceStatus;
import com.alrussy_dev.procurement_service.enums.PaymentMethods;
import com.alrussy_dev.procurement_service.service.InvoiceService;
import java.text.ParseException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/procurements")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InvoiceController {

    private final InvoiceService service;

    @GetMapping
    public ResponseEntity<PagedResult<InvoiceResponse>> findAll(@RequestParam(defaultValue = "1") Integer pageNumber) {
        return ResponseEntity.ok(service.findAll(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<InvoiceResponse> findById(@PathVariable Long number) {
        return ResponseEntity.ok(service.findByNumber(number));
    }

    @GetMapping("/filter/pages")
    public ResponseEntity<PagedResult<InvoiceResponse>> find(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "invoiceNumber") String[] sortedby,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) Long invoiceNumber,
            @RequestParam(required = false) String supplierId,
            @RequestParam(required = false) InvoiceStatus status,
            @RequestParam(required = false, name = "fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDateTime fromDate,
            @RequestParam(required = false, name = "toDate") @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDateTime toDate,
            @RequestParam(required = false) PaymentMethods paymentMethod,
            @RequestParam(required = false) Double amountGreateThan,
            @RequestParam(required = false) Double amountGreateThanOrEqual,
            @RequestParam(required = false) Double amountLessThanOrEqual,
            @RequestParam(required = false) Double amountLessThan,
            @RequestParam(required = false) Double amountEqual,
            @RequestParam(required = false) String skuCode)
            throws ParseException {

        Filter filter = new Filter(
                pageNumber,
                sortedby,
                direction,
                invoiceNumber,
                supplierId,
                status,
                fromDate != null ? fromDate : null,
                toDate,
                paymentMethod,
                amountGreateThan,
                amountGreateThanOrEqual,
                amountLessThanOrEqual,
                amountLessThan,
                amountEqual,
                skuCode);

        return ResponseEntity.ok(service.find(filter));
    }

    @PostMapping("/pending")
    public ResponseEntity<InvoiceResponse> pending(@RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(service.pending(request));
    }

    @PutMapping("/receive/{invoiceNumber}")
    public ResponseEntity<InvoiceResponse> receive(@PathVariable Long invoiceNumber) {
        return ResponseEntity.ok(service.receive(invoiceNumber));
    }

    @PutMapping("/cancel")
    public ResponseEntity<InvoiceResponse> cancel(@RequestBody ProcurementReturnRequest request) {
        return ResponseEntity.ok(service.cancel(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponse> updateInvoice(@PathVariable String id, @RequestBody InvoiceRequest request)
            throws Exception {
        System.out.println(id);
        return ResponseEntity.ok(service.update(id, request));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<InvoiceResponse> updateStatus(@PathVariable String id, @PathVariable InvoiceStatus status)
            throws Exception {

        return ResponseEntity.ok(
                service.update(id, new InvoiceRequest(null, null, null, null, null, null, status, null, null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteInvoice(@PathVariable String id) {
        return ResponseEntity.ok(service.delete(id));
    }
}
