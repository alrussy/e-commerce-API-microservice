package com.alrussy.product_service.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import com.alrussy.product_service.model.entities.id.DepartmentId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @EmbeddedId
    private DepartmentId department;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    @MapsId("categoryId")
    private Category category;

    @OneToMany(mappedBy = "department")
    private List<Product> products = new ArrayList<>();
}
