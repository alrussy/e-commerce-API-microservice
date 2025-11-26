package com.alrussy_dev.procurement_service.repository;

import com.alrussy_dev.procurement_service.entity.ProcurementReturn;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcurementReturnRepository extends MongoRepository<ProcurementReturn, String> {}
