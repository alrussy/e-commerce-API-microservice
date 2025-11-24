package com.alrussy.product_service.model.entities;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "group_categories")
@EntityListeners(AuditingEntityListener.class)
public class GroupCategory extends Audition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String imageUrl;

    @OneToMany(mappedBy = "groupCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> categories = new HashSet<Category>();

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addAllCategory(List<Category> categories) {
        categories.addAll(categories);
    }
}
