package com.alrussy.product_service.model.entities.id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ValueDetailsProductId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4964393966422903495L;

    private String value;
    private String name;
    private Long categoryId;
    private Long productId;
}
