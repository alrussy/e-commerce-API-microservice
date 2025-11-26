package com.alrussy_dev.procurement_service.repository;

import com.alrussy_dev.procurement_service.entity.Invoice;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    Optional<Invoice> findByInvoiceNumber(Long number);
}
