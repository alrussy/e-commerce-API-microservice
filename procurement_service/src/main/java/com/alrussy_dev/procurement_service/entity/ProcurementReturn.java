package com.alrussy_dev.procurement_service.entity;

import java.time.LocalDateTime;
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
@Document(collection = "procurementReturn")
public class ProcurementReturn {

    @Transient
    public static final String SEQUENCE_NAME = "return_sequence";

    @Id
    private String id;

    private Long returnNumber;
    private String invoiceId;
    private Long invoiceNumber;
    private LocalDateTime date;
    private String details;
}
