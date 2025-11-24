package com.alrussy_dev.order_service.checkout_management.repository;

import com.alrussy_dev.order_service.checkout_management.entity.Checkout;
import com.alrussy_dev.order_service.common.CheckoutStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckoutRepository extends MongoRepository<Checkout, String> {

    Optional<Checkout> findByCartId(String cartId);

    Optional<Checkout> findByIdAndCartId(String id, String cartId);

    Page<Checkout> findByStatus(CheckoutStatus status, Pageable pageable);

    Optional<Checkout> findByPayId(String payId);
}
