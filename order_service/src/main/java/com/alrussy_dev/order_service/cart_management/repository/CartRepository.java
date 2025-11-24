package com.alrussy_dev.order_service.cart_management.repository;

import com.alrussy_dev.order_service.cart_management.entity.Cart;
import com.alrussy_dev.order_service.common.CartStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByIdAndCustomerId(String id, String customerId);

    Page<Cart> findByStatus(CartStatus status, Pageable pageable);

    Optional<Cart> findByCustomerIdAndStatusEquals(String customerId, CartStatus status);

    Optional<Cart> findByCheckoutId(String id);
}
