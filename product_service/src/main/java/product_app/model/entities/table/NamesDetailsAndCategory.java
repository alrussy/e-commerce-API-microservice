package product_app.model.entities.table;

import jakarta.persistence.CascadeType;
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
import product_app.model.entities.Category;
import product_app.model.entities.NamesDetails;
import product_app.model.entities.id.NamesDetailsCategoryId;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "names_details_category")
public class NamesDetailsAndCategory {

    @EmbeddedId
    private NamesDetailsCategoryId id;

    @ManyToOne
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    @MapsId("categoryId")
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_name_id", updatable = false, insertable = false)
    @MapsId("detailNameId")
    private NamesDetails nameDetails;
}
