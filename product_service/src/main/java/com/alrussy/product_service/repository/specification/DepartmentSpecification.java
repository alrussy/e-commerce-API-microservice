package com.alrussy.product_service.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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

import com.alrussy.product_service.model.dto.department_dto.DepartmentFilter;
import com.alrussy.product_service.model.entities.Department;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSpecification implements Specification<Department> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private DepartmentFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Department> root,
            @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.name() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.name() + "%"));
        }
        if (filter.categoryId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("category").get("id"), filter.categoryId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
