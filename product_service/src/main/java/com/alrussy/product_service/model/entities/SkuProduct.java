package com.alrussy.product_service.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alrussy.product_service.model.entities.table.DetailsOfSku;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "sku-products")
@EntityListeners(AuditingEntityListener.class)
public class SkuProduct extends Audition {

    @Id
    //  @GeneratedValue(strategy = GenerationType.UUID)
    private String skuCode;

    @ManyToOne
    private Product product;

    private Double price;
    private Double discount;
    private String currency;
    private Boolean isPrimary;

    @ElementCollection
    @Builder.Default
    private List<String> imageUrls = new ArrayList();

    @Transient
    private Double priceAfterDiscount;

    @OneToMany(mappedBy = "skuProduct", cascade = CascadeType.ALL)
    private Set<DetailsOfSku> details = new HashSet<>();

    public void addImage(String filename) {
        imageUrls.add(filename);
    }

    public void removeImage(String filename) {
        imageUrls.remove(filename);
    }

    public void addDetail(DetailsOfSku detail) {
        this.details.add(detail);
    }

    public void addDetail(List<DetailsOfSku> details) {

        this.details.addAll(details);
    }

    public void removeDetail(DetailsOfSku detail) {
        this.details.remove(detail);
    }

    @PostLoad
    public void setPriceAfterDiscount() {
        if (price == null) {
            price = 0.0;
        }
        if (discount == null) {
            discount = 0.0;
        }
        priceAfterDiscount = price - price * discount / 100;
    }
}
