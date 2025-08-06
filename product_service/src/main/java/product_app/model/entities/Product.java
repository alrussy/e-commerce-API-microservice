package product_app.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import product_app.model.entities.id.ProductId;
import product_app.model.entities.table.BrandCategory;
import product_app.model.entities.table.ValueDetailsAndProduct;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(
        name = "products",
        uniqueConstraints =
                @UniqueConstraint(
                        columnNames = {
                            "product_id",
                            "name",
                        }))
@EntityListeners(AuditingEntityListener.class)
public class Product extends Audition {

    @EmbeddedId
    private ProductId id;

    @Column(unique = true)
    private String name;

    private String imageUrl;
    private String about;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToOne
    @JoinColumnsOrFormulas(
            value = {
                @JoinColumnOrFormula(formula = @JoinFormula(value = "category_id")),
                @JoinColumnOrFormula(column = @JoinColumn(name = "department_id"))
            })
    private Department department;

    @ManyToOne(targetEntity = BrandCategory.class, fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas(
            value = {
                @JoinColumnOrFormula(formula = @JoinFormula(value = "category_id")),
                @JoinColumnOrFormula(formula = @JoinFormula(value = "brand_id"))
            })
    private BrandCategory brandCategory;

    @OneToMany(mappedBy = "product", targetEntity = ValueDetailsAndProduct.class, cascade = CascadeType.ALL)
    private Set<ValueDetailsAndProduct> details = new HashSet<>();
}
