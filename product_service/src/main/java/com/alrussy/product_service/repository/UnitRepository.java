package com.alrussy.product_service.repository;

import com.alrussy.product_service.model.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {}
