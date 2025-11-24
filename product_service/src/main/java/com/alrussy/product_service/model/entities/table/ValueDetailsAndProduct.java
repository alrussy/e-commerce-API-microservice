package com.alrussy.product_service.model.entities.table;

import com.alrussy.product_service.model.entities.Product;
import com.alrussy.product_service.model.entities.id.ValueDetailsProductId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
        name = "value_details_product",
        uniqueConstraints =
                @UniqueConstraint(
                        columnNames = {
                            "product_id",
                            "value",
                        }))
public class ValueDetailsAndProduct {

    @EmbeddedId
    private ValueDetailsProductId id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumns(
            value = {
                @JoinColumn(name = "categoryId", updatable = false, insertable = false),
                @JoinColumn(name = "name", updatable = false, insertable = false)
            })
    private NamesDetailsAndCategory namesDetailsAndCategory;

    @ManyToOne
    @JoinColumns(
            value = {
                @JoinColumn(name = "categoryId", updatable = false, insertable = false),
                @JoinColumn(name = "productId", updatable = false, insertable = false)
            })
    private Product product;
}
