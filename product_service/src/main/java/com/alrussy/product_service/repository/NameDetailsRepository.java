package com.alrussy.product_service.repository;

import com.alrussy.product_service.model.entities.NamesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameDetailsRepository extends JpaRepository<NamesDetails, String> {}
