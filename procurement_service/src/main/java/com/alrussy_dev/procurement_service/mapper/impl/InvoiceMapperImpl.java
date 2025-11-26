package com.alrussy_dev.procurement_service.mapper.impl;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.alrussy_dev.procurement_service.dto.InvoiceRequest;
import com.alrussy_dev.procurement_service.dto.InvoiceResponse;
import com.alrussy_dev.procurement_service.dto.LineProductResponse;
import com.alrussy_dev.procurement_service.dto.ProcurementReturnRequest;
import com.alrussy_dev.procurement_service.dto.ProcurementReturnResponse;
import com.alrussy_dev.procurement_service.dto.SupplierResponse;
import com.alrussy_dev.procurement_service.entity.Invoice;
import com.alrussy_dev.procurement_service.entity.ProcurementReturn;
import com.alrussy_dev.procurement_service.entity.Supplier;
import com.alrussy_dev.procurement_service.entity.sequences.InvoiceSequence;
import com.alrussy_dev.procurement_service.enums.InvoiceStatus;
import com.alrussy_dev.procurement_service.mapper.InvoiceMapper;
import com.alrussy_dev.procurement_service.mapper.LineProductMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class InvoiceMapperImpl implements InvoiceMapper {
    private final MongoOperations mongoOperations;
    private final LineProductMapper lineProductMapper;

    private Long generateSequence(String seqName) {
        InvoiceSequence counter = mongoOperations.findAndModify(
                Query.query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                InvoiceSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    @Override
    public Invoice toEntity(InvoiceRequest request) {
        List<LineProductResponse> lineProducts = request.lineProducts() == null
                ? null
                : request.lineProducts().stream()
                        .map(lineProductMapper::toEntity)
                        .map(lineProductMapper::fromEntity)
                        .toList();
        Long ivoiceNumber = generateSequence(Invoice.SEQUENCE_NAME);
        var amount = lineProducts == null
                ? null
                : lineProducts.stream().mapToDouble(t -> t.totalLine()).sum();
        return Invoice.builder()
                .supplier(Supplier.builder().id(request.supplierId()).build())
                .status(InvoiceStatus.PENDING)
                .invoiceNumber(ivoiceNumber)
                .date(request.date() == null ? LocalDateTime.now() : request.date())
                .currency(request.currency())
                .details(request.details())
                .paymentMethod(request.paymentMethod())
                .refranceNumber(request.refranceNumber())
                .lineProducts(
                        request.lineProducts() == null
                                ? null
                                : request.lineProducts().stream()
                                        .map(t -> lineProductMapper.toEntity(t))
                                        .toList())
                .amount(amount)
                .build();
    }

    @Override
    public InvoiceResponse fromEntity(Invoice entity) {
        List<LineProductResponse> lineProducts = entity.getLineProducts() == null
                ? null
                : entity.getLineProducts().stream()
                        .map(lineProductMapper::fromEntity)
                        .toList();
        var amount = lineProducts == null
                ? null
                : lineProducts.stream().mapToDouble(t -> t.totalLine()).sum();
        return new InvoiceResponse(
                entity.getId(),
                entity.getInvoiceNumber(),
                entity.getRefranceNumber(),
                new SupplierResponse(
                        entity.getSupplier().getId(), entity.getSupplier().getName(), null, null, null),
                entity.getDate(),
                entity.getPaymentMethod(),
                entity.getDetails(),
                entity.getStatus(),
                entity.getCurrency(),
                amount,
                lineProducts,
                entity.getProcurementReturn() != null
                        ? new ProcurementReturnResponse(
                                entity.getProcurementReturn().getId(),
                                entity.getProcurementReturn().getReturnNumber(),
                                entity.getProcurementReturn().getDate(),
                                entity.getProcurementReturn().getDetails(),
                                entity.getCurrency(),
                                amount,
                                lineProducts)
                        : null);
    }

    @Override
    public Invoice addReturn(Invoice invoice, ProcurementReturnRequest request) {
        invoice.setProcurementReturn(ProcurementReturn.builder()
                .returnNumber(generateSequence(ProcurementReturn.SEQUENCE_NAME))
                .date(request.date())
                .invoiceNumber(invoice.getInvoiceNumber())
                .details(request.details())
                .build());
        invoice.setStatus(InvoiceStatus.CANCELLED);
        return invoice;
    }
}
