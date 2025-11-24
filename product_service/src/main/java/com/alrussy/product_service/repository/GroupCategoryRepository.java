package com.alrussy.product_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alrussy.product_service.model.entities.GroupCategory;

public interface GroupCategoryRepository
        extends JpaRepository<GroupCategory, Long>, JpaSpecificationExecutor<GroupCategory> {

    List<GroupCategory> findByNameLike(String name);
}
