package product_app.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
import product_app.model.entities.table.BrandCategory;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brands")
@EntityListeners(AuditingEntityListener.class)
public class Brand extends Audition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String imageUrl;

    @OneToMany(mappedBy = "brand", targetEntity = BrandCategory.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<BrandCategory> brandCategory = new HashSet<BrandCategory>();

    public void addCategory(BrandCategory category) {
        brandCategory.add(category);
    }

    public void addAllCategory(List<BrandCategory> categories) {
        brandCategory.addAll(categories);
    }
}
