package com.alrussy.product_service.repository.specification;

import com.alrussy.product_service.model.dto.category_dto.CategoryFilter;
import com.alrussy.product_service.model.entities.Category;
import com.alrussy.product_service.model.entities.Department;
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
public class CategorySpecification implements Specification<Category> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private CategoryFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Category> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.name() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.name() + "%"));
        }
        if (filter.brandId() != null) {
            Join<Category, BrandCategory> brands = root.join("brandCategory");
            predicates.add(criteriaBuilder.equal(brands.get("brand").get("id"), filter.brandId()));
        }
        if (filter.groupId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("groupCategory").get("id"), filter.groupId()));
        }
        if (filter.departmentId() != null) {
            Join<Category, Department> department = root.join("departments");

            predicates.add(criteriaBuilder.equal(department.get("department").get("id"), filter.departmentId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
