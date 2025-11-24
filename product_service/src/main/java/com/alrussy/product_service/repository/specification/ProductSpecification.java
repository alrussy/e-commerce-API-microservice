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

import com.alrussy.product_service.model.*;
import com.alrussy.product_service.model.dto.product_dto.ProductFilter;
import com.alrussy.product_service.model.entities.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSpecification implements Specification<Product> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ProductFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<Product> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.name() + "%"));
        }
        if (filter.categoryId() != null) {
            predicates.add(
                    criteriaBuilder.equal(root.get("department").get("category").get("id"), filter.categoryId()));
        }
        if (filter.brandId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("brand").get("id"), filter.brandId()));
        }
        if (filter.groupId() != null) {
            predicates.add(criteriaBuilder.equal(
                    root.get("department").get("category").get("groupCategory").get("id"), filter.groupId()));
        }
        if (filter.departmentId() != null) {
            predicates.add(criteriaBuilder.equal(
                    root.get("department").get("department").get("id"), filter.departmentId()));
        }
        //		if(filter.price()!=null){
        //			if(filter.price().type() == PriceFilter.TypeFilter.EQUEL) {
        //				 predicates.add(criteriaBuilder.equal(root.get("price"),filter.price().price()));
        //			}
        //			else if(filter.price().type() == PriceFilter.TypeFilter.GREATE_THAN) {
        //				 predicates.add(criteriaBuilder.greaterThan(root.get("price"),filter.price().price()));
        //			}
        //			else if(filter.price().type() == PriceFilter.TypeFilter.GREATE_THAN_OR_EQUEL) {
        //				 predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"),filter.price().price()));
        //			}
        //			else if(filter.price().type() == PriceFilter.TypeFilter.LESS_THAN_OR_EQUEL) {
        //				 predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"),filter.price().price()));
        //			}
        //			else
        //				 predicates.add(criteriaBuilder.lessThan(root.get("price"),filter.price().price()));
        //
        //		}
        //
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
