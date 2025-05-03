package product_app.model.entities.id;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class ProductId implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -293757139257770506L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private Long categoryId;
}
