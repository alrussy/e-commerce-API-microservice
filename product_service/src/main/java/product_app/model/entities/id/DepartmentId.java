package product_app.model.entities.id;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
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
public class DepartmentId implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -293757139257770506L;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmaent_generator")
    @SequenceGenerator(name = "departmaent_generator", sequenceName = "department_seq")
    private Long id;

    private Long categoryId;
}
