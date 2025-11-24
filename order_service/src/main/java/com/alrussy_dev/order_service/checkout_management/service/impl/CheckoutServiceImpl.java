package com.alrussy_dev.order_service.checkout_management.service.impl;

import com.alrussy_dev.order_service.Event.AddedAddressEvent;
import com.alrussy_dev.order_service.Event.AddedPaymentMethodEvent;
import com.alrussy_dev.order_service.Event.CreatedOrderEvent;
import com.alrussy_dev.order_service.Event.StartedCheckoutEvent;
import com.alrussy_dev.order_service.checkout_management.entity.Checkout;
import com.alrussy_dev.order_service.checkout_management.repository.CheckoutRepository;
import com.alrussy_dev.order_service.checkout_management.service.CheckoutService;
import com.alrussy_dev.order_service.common.CheckoutStatus;
import com.alrussy_dev.order_service.common.PageMapper;
import com.alrussy_dev.order_service.common.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository repository;
    private final PageMapper<Checkout> pageMapper;

    @KafkaListener(
            topics = {"${orders.topic-started-checkout}"},
            groupId = "query",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void startCheckout(StartedCheckoutEvent event) {
        Checkout checkout = Checkout.builder()
                .id(event.aggregateId())
                .status(CheckoutStatus.STARTED)
                .build();
        repository.save(checkout);
    }

    @KafkaListener(
            topics = {"${orders.topic-added-address}"},
            groupId = "query-checkout",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void setAddress(AddedAddressEvent event) {
        Checkout checkout = repository
                .findById(event.aggregateId())
                .orElseThrow(
                        () -> new RuntimeException("The Checkout By This ID : " + event.aggregateId() + " Not Found"));
        if (checkout.getStatus().equals(CheckoutStatus.STARTED)) {
            checkout.setAddress(event.address());
            repository.save(checkout);
        }
    }

    @KafkaListener(
            topics = {"${orders.topic-added-payment-method}"},
            groupId = "query-checkout",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void setPaymentMethod(AddedPaymentMethodEvent event) {
        Checkout checkout = repository
                .findById(event.aggregateId())
                .orElseThrow(
                        () -> new RuntimeException("The Checkout By This ID : " + event.aggregateId() + " Not Found"));
        checkout.setPaymentMethod(event.paymentMethod());
        repository.save(checkout);
    }

    @Override
    public Checkout cancelCheckout(String id) {
        Checkout checkout = repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("The Checkout By This ID : " + id + " Not Found"));
        if (checkout.getStatus().equals(CheckoutStatus.STARTED)) {
            repository.delete(checkout);
        }
        return checkout;
    }

    @KafkaListener(
            topics = {"${orders.topic-created-order}"},
            groupId = "query",
            containerFactory = "kafkaListenerContainerFactory2")
    @Override
    public void createdOrder(CreatedOrderEvent event) {
        Checkout checkout = repository
                .findById(event.aggregateId())
                .orElseThrow(
                        () -> new RuntimeException("The Checkout By This ID : " + event.aggregateId() + " Not Found"));
        checkout.setStatus(CheckoutStatus.COMPLETED);
        repository.save(checkout);
    }

    @Override
    public Checkout cancelPay(String payId) {
        Checkout checkout = repository
                .findByPayId(payId)
                .orElseThrow(() -> new RuntimeException("The Checkout By This Order_ID : " + payId + " Not Found"));
        if (checkout.getAddress() != null && checkout.getPaymentMethod() != null) {
            checkout.setPayId(null);
            checkout.setStatus(CheckoutStatus.STARTED);
            return repository.save(checkout);
        }
        return null;
    }

    @Override
    public Checkout findById(String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("The Checkout By This ID : " + id + " Not Found"));
    }

    @Override
    public Checkout findByCartId(String cartId) {
        return repository
                .findByCartId(cartId)
                .orElseThrow(() -> new RuntimeException("The Checkout By This Cart_ID : " + cartId + " Not Found"));
    }

    @Override
    public Checkout findByIdAndCartId(String id, String cartId) {
        // TODO Auto-generated method stub
        return repository
                .findByIdAndCartId(id, cartId)
                .orElseThrow(() -> new RuntimeException(
                        "The Checkout By This ID " + id + " And Cart_ID : " + cartId + " Not Found"));
    }

    @Override
    public PagedResult<Checkout> findByStatus(CheckoutStatus status, Integer pageNumper, Integer sizePage) {
        Pageable pageable = PageRequest.of(pageNumper, sizePage);
        Page<Checkout> page = repository.findByStatus(status, pageable);
        return pageMapper.toPageResponse(page);
    }

    @Override
    public PagedResult<Checkout> findAll(Integer pageNumper, Integer sizePage) {
        Pageable pageable = PageRequest.of(pageNumper, sizePage);
        Page<Checkout> page = repository.findAll(pageable);
        return pageMapper.toPageResponse(page);
    }
}
