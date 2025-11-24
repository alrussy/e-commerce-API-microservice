package com.alrussy.product_service.model.entities.table;

import com.alrussy.product_service.model.entities.SkuProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(
        name = "details_sku",
        uniqueConstraints =
                @UniqueConstraint(
                        columnNames = {
                            "value_details_and_product_name",
                            "sku",
                        }))
public class DetailsOfSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ValueDetailsAndProduct valueDetailsAndProduct;

    @ManyToOne
    @JoinColumn(name = "sku")
    private SkuProduct skuProduct;
}
