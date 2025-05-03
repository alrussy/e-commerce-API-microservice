package product_app.model.entities.table;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product_app.model.entities.Brand;
import product_app.model.entities.Category;
import product_app.model.entities.id.BrandCategoryId;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "brand_category")
public class BrandCategory {

    @EmbeddedId
    private BrandCategoryId brandCategoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    @MapsId("categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brandId", updatable = false, insertable = false)
    // @MapsId("brandId")
    private Brand brand;
}
