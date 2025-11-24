package com.alrussy.product_service.repository.specification;

import com.alrussy.product_service.model.dto.brand_dto.BrandFilter;
import com.alrussy.product_service.model.entities.Brand;
import com.alrussy.product_service.model.entities.table.BrandCategory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandSpecification implements Specification<Brand> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BrandFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Brand> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.name() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.name() + "%"));
        }

        if (filter.categoryId() != null) {
            Join<Brand, BrandCategory> brands = root.join("brandCategory");
            predicates.add(criteriaBuilder.equal(brands.get("category").get("id"), filter.categoryId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
