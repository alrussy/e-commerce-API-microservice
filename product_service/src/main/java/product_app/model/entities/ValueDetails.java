package product_app.model.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import product_app.model.entities.id.ValueDetailsId;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "values_details")
public class ValueDetails {

    @EmbeddedId
    private ValueDetailsId valueDetails;

    @ManyToOne
    @JoinColumn(name = "name", updatable = false, insertable = false)
    private NamesDetails nameDetails;
}
