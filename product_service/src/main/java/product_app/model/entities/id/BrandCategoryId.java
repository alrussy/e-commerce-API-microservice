package product_app.model.entities.id;

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
public class BrandCategoryId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long categoryId;

    private Long brandId;
}
