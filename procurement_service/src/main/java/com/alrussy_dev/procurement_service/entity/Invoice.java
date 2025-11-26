package com.alrussy_dev.procurement_service.entity;

import com.alrussy_dev.procurement_service.enums.InvoiceStatus;
import com.alrussy_dev.procurement_service.enums.PaymentMethods;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "invoice")
public class Invoice {
    @Transient
    public static final String SEQUENCE_NAME = "invoice_sequence";

    @Id
    private String id;

    private Long invoiceNumber;
    private Supplier supplier;
    private InvoiceStatus status;
    private LocalDateTime date;
    private PaymentMethods paymentMethod;
    private String refranceNumber;
    private String currency;
    private String details;
    private Double amount;

    private ProcurementReturn procurementReturn;
    private List<LineProduct> lineProducts;
}
