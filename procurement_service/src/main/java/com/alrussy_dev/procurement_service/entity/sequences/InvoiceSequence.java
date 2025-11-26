package com.alrussy_dev.procurement_service.entity.sequences;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "invoice_sequence")
public class InvoiceSequence {

    @Id
    private String id;

    private Long seq;
}
