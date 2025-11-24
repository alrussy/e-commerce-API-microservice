package com.alrussy.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alrussy.product_service.model.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {}
