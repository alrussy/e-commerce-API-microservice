package product_app.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import product_app.model.entities.table.NamesDetailsAndCategory;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "names_details")
public class NamesDetails {

    @Id
    private String name;

    @OneToMany(
            mappedBy = "nameDetails",
            targetEntity = NamesDetailsAndCategory.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<NamesDetailsAndCategory> nameDetailsCategory = new HashSet<>();

    @OneToMany(
            mappedBy = "nameDetails",
            targetEntity = ValueDetails.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ValueDetails> values = new ArrayList<>();
}
