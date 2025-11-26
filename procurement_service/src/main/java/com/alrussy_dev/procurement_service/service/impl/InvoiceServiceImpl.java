package com.alrussy_dev.procurement_service.service.impl;

import com.alrussy_dev.procurement_service.client.InventoryService;
import com.alrussy_dev.procurement_service.client.LineProductOfInventory;
import com.alrussy_dev.procurement_service.dto.Filter;
import com.alrussy_dev.procurement_service.dto.InvoiceRequest;
import com.alrussy_dev.procurement_service.dto.InvoiceResponse;
import com.alrussy_dev.procurement_service.dto.PagedResult;
import com.alrussy_dev.procurement_service.dto.ProcurementReturnRequest;
import com.alrussy_dev.procurement_service.entity.Invoice;
import com.alrussy_dev.procurement_service.enums.InvoiceStatus;
import com.alrussy_dev.procurement_service.mapper.InvoiceMapper;
import com.alrussy_dev.procurement_service.mapper.LineProductMapper;
import com.alrussy_dev.procurement_service.mapper.PageMapper;
import com.alrussy_dev.procurement_service.mapper.impl.LineProductMapperImpl;
import com.alrussy_dev.procurement_service.publish.CancelledInvoiceProcurementEvent;
import com.alrussy_dev.procurement_service.publish.PendedInvoiceProcurementEvent;
import com.alrussy_dev.procurement_service.publish.Publisher;
import com.alrussy_dev.procurement_service.publish.ReceivedInvoiceProcurementEvent;
import com.alrussy_dev.procurement_service.publish.UpdatedInvoiceProcurementEvent;
import com.alrussy_dev.procurement_service.repository.InvoiceRepository;
import com.alrussy_dev.procurement_service.service.InvoiceService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final Publisher publisher;
    private final InventoryService inventoryService;
    private final PageMapper<InvoiceResponse> pageMapper;
    private final InvoiceMapper mapper;
    private final InvoiceRepository repository;
    private final MongoTemplate mongoTemplate;

    @Override
    public PagedResult<InvoiceResponse> findAll(int pageNumber) {
        var pageaple = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);
        return pageMapper.toPageResponse(repository.findAll(pageaple).map(mapper::fromEntity));
    }

    @Override
    public InvoiceResponse findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long delete(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InvoiceResponse save(InvoiceRequest request) {
        InvoiceResponse invoiceResponse = mapper.fromEntity(repository.save(mapper.toEntity(request)));
        return invoiceResponse;
    }

    @Override
    public InvoiceResponse pending(InvoiceRequest request) {
        InvoiceResponse invoiceResponse = save(request);
        List<LineProductOfInventory> lineProductOfInventory = invoiceResponse.lineProducts().stream()
                .map(line -> new LineProductOfInventory(line.skuCode(), line.quantity()))
                .toList();
        publisher.publish(new PendedInvoiceProcurementEvent(
                invoiceResponse.invoiceNumber().toString(), lineProductOfInventory));
        return invoiceResponse;
    }

    @Override
    public InvoiceResponse receive(Long invoiceNumber) {
        Invoice invoiceFind = repository.findByInvoiceNumber(invoiceNumber).orElseThrow();
        invoiceFind.setStatus(InvoiceStatus.RECEIVED);
        InvoiceResponse invoiceResponse = mapper.fromEntity(repository.save(invoiceFind));
        publisher.publish(new ReceivedInvoiceProcurementEvent(invoiceNumber.toString()));

        return invoiceResponse;
    }

    @Override
    public InvoiceResponse cancel(ProcurementReturnRequest procurementReturn) {

        Invoice invoiceFind = repository
                .findByInvoiceNumber(procurementReturn.invoiceNumber())
                .orElseThrow();
        var invoice = mapper.addReturn(invoiceFind, procurementReturn);
        InvoiceResponse invoiceResponse = mapper.fromEntity(repository.save(invoice));
        List<LineProductOfInventory> lineProductOfInventory = invoiceFind.getLineProducts().stream()
                .map(line -> new LineProductOfInventory(line.getSkuCode(), line.getQuantity()))
                .toList();
        publisher.publish(new CancelledInvoiceProcurementEvent(
                invoiceResponse.procurementReturnResponse().returnNumber().toString(), lineProductOfInventory));
        return invoiceResponse;
    }

    @Transactional
    @Override
    public InvoiceResponse update(String id, InvoiceRequest request) throws Exception {
        var invoice = repository.findById(id).orElseThrow(() -> new RuntimeException("entity not foun by id " + id));
        if (request.date() != null) {
            invoice.setDate(request.date());
        }
        if (request.currency() != null && !request.currency().equals(invoice.getCurrency())) {
            invoice.setCurrency(request.currency());
        }
        if (request.details() != null) {
            invoice.setDetails(request.details());
        }
        if (request.refranceNumber() != null) {
            invoice.setRefranceNumber(request.refranceNumber());
        }

        if (request.paymentMethod() != null) {
            invoice.setPaymentMethod(request.paymentMethod());
        }
        if (request.lineProducts() != null) {
            invoice.getLineProducts().clear();
            invoice = repository.save(invoice);
            LineProductMapper lineproductMapper = new LineProductMapperImpl();
            invoice.setLineProducts(request.lineProducts().stream()
                    .map(t -> lineproductMapper.toEntity(t))
                    .toList());

            invoice.setAmount(invoice.getLineProducts().stream()
                    .map(lineproductMapper::fromEntity)
                    .mapToDouble(t -> t.totalLine())
                    .sum());
        }
        if (request.status() != null && !request.status().equals(invoice.getStatus())) {

            invoice.setStatus(request.status());
        }
        if (request.lineProducts() != null) {

            invoice.setLineProducts(request.lineProducts().stream()
                    .map(line -> new LineProductMapperImpl().toEntity(line))
                    .toList());
            List<LineProductOfInventory> lineProductOfInventory = invoice.getLineProducts().stream()
                    .map(line -> new LineProductOfInventory(line.getSkuCode(), line.getQuantity()))
                    .toList();
            publisher.publish(new UpdatedInvoiceProcurementEvent(
                    invoice.getInvoiceNumber().toString(), lineProductOfInventory));
        }
        var response = mapper.fromEntity(repository.save(invoice));
        return response;
    }

    @Override
    public Long count() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InvoiceResponse findByNumber(Long number) {
        Optional<Invoice> invoice = repository.findByInvoiceNumber(number);
        if (invoice.isPresent()) {
            return mapper.fromEntity(invoice.get());
        } else {
            return null;
        }
    }

    @Override
    public PagedResult<InvoiceResponse> find(Filter filter) {

        Pageable pageable = PageRequest.of(
                filter.pageNumber() <= 1 ? 0 : filter.pageNumber() - 1,
                10,
                Sort.by(Arrays.asList(filter.sortedby()).stream()
                        .map(s -> filter.direction().equalsIgnoreCase("asc")
                                ? Order.asc(getFieldName(s))
                                : Order.desc(getFieldName(s)))
                        .toList()));

        Query query = new Query().with(pageable);
        List<Criteria> criterias = new ArrayList<>();

        if (filter.invoiceNumber() != null) {
            criterias.add(Criteria.where("invoiceNumber").is(filter.invoiceNumber()));
        }
        if (filter.status() != null) {
            criterias.add(Criteria.where("status").is(filter.status()));
        }
        if (filter.paymentMethod() != null) {
            criterias.add(Criteria.where("paymentMethod").is(filter.paymentMethod()));
        }
        if (filter.skuCode() != null) {
            criterias.add(Criteria.where("lineProducts.skuCode").is(filter.skuCode()));
        }

        if (filter.fromDate() != null || filter.toDate() != null) {
            // criterias.add(Criteria.where("date").is(filter.date()));
            System.out.println();
            criterias.add(Criteria.where("date").gte(filter.fromDate()).lte(filter.toDate()));
        }
        if (filter.supplierId() != null) {
            criterias.add(Criteria.where("supplier.id").gte(filter.supplierId()).lte("1231"));
        }

        if (filter.amountEqual() != null) {
            criterias.add(Criteria.where("amount").is(filter.amountEqual()));
        } else if (filter.amountLessThan() != null) {
            criterias.add(Criteria.where("amount").lt(filter.amountLessThan()));
        } else if (filter.amountLessThanOrEqual() != null) {
            criterias.add(Criteria.where("amount").lte(filter.amountLessThanOrEqual()));
        } else if (filter.amountGreateThanOrEqual() != null) {
            criterias.add(Criteria.where("amount").gte(filter.amountGreateThanOrEqual()));
        } else if (filter.amountGreateThan() != null) {
            criterias.add(Criteria.where("amount").gt(filter.amountGreateThan()));
        }

        if (!criterias.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[0])));
        }

        Page<Invoice> page = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Invoice.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Invoice.class));

        return pageMapper.toPageResponse(page.map(mapper::fromEntity));
    }

    private String getFieldName(String content) {
        if (content.toLowerCase().startsWith("supplier")) {
            return "supplierId";
        } else return content;
    }
}
