package com.alrussy_dev.order_service.cart_management.service.impl;

import com.alrussy_dev.order_service.Event.AddedProductEvent;
import com.alrussy_dev.order_service.Event.ChangedQuantityEvent;
import com.alrussy_dev.order_service.Event.CreatedOrderEvent;
import com.alrussy_dev.order_service.Event.OpenedNewCartEvent;
import com.alrussy_dev.order_service.Event.RemovedProductEvent;
import com.alrussy_dev.order_service.cart_management.entity.Cart;
import com.alrussy_dev.order_service.cart_management.repository.CartRepository;
import com.alrussy_dev.order_service.cart_management.service.CartService;
import com.alrussy_dev.order_service.common.CartStatus;
import com.alrussy_dev.order_service.common.PageMapper;
import com.alrussy_dev.order_service.common.PagedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository repository;
    private final PageMapper<Cart> pageMapper;

    @KafkaListener(
            topics = {"${orders.topic-opened-new-cart}"},
            groupId = "query-cart",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void openNewCart(OpenedNewCartEvent event) {
        repository
                .save(Cart.builder()
                        .id(event.aggregateId())
                        .customerId(event.customerId())
                        .status(event.status())
                        .build())
                .getId();
    }

    @KafkaListener(
            topics = {"${orders.topic-added-product-to-cart}"},
            groupId = "query-cart",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void addLineProduct(AddedProductEvent event) {
        Cart cart = repository.findById(event.aggregateId()).orElse(null);
        if (cart != null && event.lineProduct() != null) {
            cart.addLineProduct(event.lineProduct());
            repository.save(cart);
        }
    }

    @KafkaListener(
            topics = {"${orders.topic-removed-product-from-cart}"},
            groupId = "query-cart",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void removeLineProduct(RemovedProductEvent event) {

        Cart cart = repository.findById(event.aggregateId()).orElse(null);
        if (cart != null && event.skuCode() != null) {
            cart.removeLineProduct(cart.getLineProducts().stream()
                    .filter(line -> line.getSkuCode().equals(event.skuCode()))
                    .findFirst()
                    .get());
            repository.save(cart);
        }
    }

    @KafkaListener(
            topics = {"${orders.topic-changed-quantity}"},
            groupId = "query-cart",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void changeQuantity(ChangedQuantityEvent event) {

        Cart cart = repository.findById(event.aggregateId()).orElse(null);

        if (cart != null) {
            var lineProduct = cart.getLineProducts().stream()
                    .filter(line -> line.getSkuCode().equals(event.skuCode()))
                    .findFirst()
                    .get();
            lineProduct.setQuantity(event.quantity());
            repository.save(cart);
        }
    }

    @KafkaListener(
            topics = {"${orders.topic-created-order}"},
            groupId = "query-cart",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void closeCart(CreatedOrderEvent event) {
        Cart cart = repository.findById(event.aggregateId()).orElse(null);
        cart.setStatus(CartStatus.CLOSED);
        repository.save(cart);
    }

    @Override
    public Cart findCartById(String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("The Cart By This ID" + id + "Not Found"));
    }

    @Override
    public PagedResult<Cart> findAll(Integer pageNumper, Integer sizePage) {

        Pageable pageable = PageRequest.of(pageNumper, sizePage);
        return pageMapper.toPageResponse(repository.findAll(pageable));
    }

    @Override
    public PagedResult<Cart> findByStatus(CartStatus status, Integer pageNumper, Integer sizePage) {
        Pageable pageable = PageRequest.of(pageNumper, sizePage);

        return pageMapper.toPageResponse(repository.findByStatus(status, pageable));
    }

    @Override
    public Cart findCartByCustomrerId(String customerId) {
        // TODO Auto-generated method stub
        return repository
                .findByCustomerIdAndStatusEquals(customerId, CartStatus.OPENED)
                .orElseThrow(() -> new RuntimeException("The Cart By This Username : " + customerId + "Not Found"));
    }
}
