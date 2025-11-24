package com.alrussy.product_service.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alrussy.product_service.model.entities.table.BrandCategory;
import com.alrussy.product_service.model.entities.table.NamesDetailsAndCategory;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
@EntityListeners(AuditingEntityListener.class)
public class Category extends Audition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String imageUrl;

    @OneToMany(mappedBy = "category", targetEntity = BrandCategory.class)
    private List<BrandCategory> brandCategory = new ArrayList<>();

    @ManyToOne
    private GroupCategory groupCategory;

    @OneToMany(
            mappedBy = "category",
            targetEntity = NamesDetailsAndCategory.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<NamesDetailsAndCategory> nameDetailsCategory = new HashSet<>();

    @OneToMany(mappedBy = "category", targetEntity = Department.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments = new ArrayList<>();

    public void addDepartemnt(Department department) {

        this.departments.add(department);
    }

    public void addNameDetailst(Set<NamesDetailsAndCategory> namesDetails) {

        this.nameDetailsCategory.addAll(namesDetails);
    }

    public void addDepartemnt(List<Department> departments) {
        this.departments.addAll(departments);
    }

    public void removeDepartemnt(Department department) {
        this.departments.remove(department);
    }

    public void removeDepartemnt() {
        departments.clear();
    }
}
