package com.alrussy.product_service.repository.specification;

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

import com.alrussy.product_service.model.dto.group_category_dto.GroupFilter;
import com.alrussy.product_service.model.entities.Category;
import com.alrussy.product_service.model.entities.GroupCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupSpecification implements Specification<GroupCategory> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private GroupFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<GroupCategory> root,
            @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.name() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.name() + "%"));
        }
        if (filter.categoryId() != null) {
            Join<GroupCategory, Category> categories = root.join("categories");

            predicates.add(criteriaBuilder.equal(categories.get("id"), filter.categoryId()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
