package com.alrussy.product_service.repository.specification;

import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductFilter;
import com.alrussy.product_service.model.entities.SkuProduct;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkuProductSpecification implements Specification<SkuProduct> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private SkuProductFilter filter;

    @Override
    public Predicate toPredicate(
            @NonNull Root<SkuProduct> root,
            @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.skuCode() != null) {

            predicates.add(criteriaBuilder.like(root.get("skuCode"), "%" + filter.skuCode() + "%"));
        }
        if (filter.productId() != null) {

            predicates.add(criteriaBuilder.equal(root.get("product").get("id").get("productId"), filter.productId()));
        }

        if (filter.departmentId() != null) {
            predicates.add(criteriaBuilder.equal(
                    root.get("product").get("department").get("department").get("id"), filter.departmentId()));
        }
        if (filter.brandId() != null) {
            predicates.add(
                    criteriaBuilder.equal(root.get("product").get("brand").get("id"), filter.brandId()));
        }
        if (filter.groupId() != null) {
            predicates.add(criteriaBuilder.equal(
                    root.get("product")
                            .get("department")
                            .get("category")
                            .get("groupCategory")
                            .get("id"),
                    filter.groupId()));
        }
        if (filter.categoryId() != null) {
            predicates.add(criteriaBuilder.equal(
                    root.get("product").get("department").get("category").get("id"), filter.categoryId()));
        }
        if (filter.priceEquel() != null) {
            predicates.add(criteriaBuilder.equal(root.get("price"), filter.priceEquel()));
        }
        if (filter.priceGreateThan() != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("price"), filter.priceGreateThan()));
        }
        if (filter.priceGreateThanOrEquel() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.priceGreateThanOrEquel()));
        }
        if (filter.priceLessThanOrEquel() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.priceLessThanOrEquel()));
        }
        if (filter.priceLessThan() != null) {

            predicates.add(criteriaBuilder.lessThan(root.get("price"), filter.priceLessThan()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
