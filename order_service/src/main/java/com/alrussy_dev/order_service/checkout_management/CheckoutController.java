package com.alrussy_dev.order_service.checkout_management;

import com.alrussy_dev.order_service.checkout_management.entity.Checkout;
import com.alrussy_dev.order_service.checkout_management.service.CheckoutService;
import com.alrussy_dev.order_service.common.CheckoutStatus;
import com.alrussy_dev.order_service.common.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService service;

    //	@PostMapping
    //	Checkout startCheckout(@PathVariable String cartId) {
    //		return service.startCheckout(cartId);
    //	}
    //
    //	@PutMapping("/address/{id}")
    //	Checkout setAddress(@PathVariable String id,@RequestBody Address address) {
    //		return service.setAddress(id, address);
    //	}
    //	@PutMapping("/payment-method/{id}")
    //	Checkout setPaymentMethod(@PathVariable String id,@RequestParam PaymentMethod paymentMethod) {
    //		return service.setPaymentMethod(id, paymentMethod);
    //
    //	}
    @DeleteMapping("/{id}")
    Checkout cancelCheckout(@PathVariable String id) {
        return service.cancelCheckout(id);
    }

    //	@PutMapping("/pay/{id}")
    //	Checkout pay(@PathVariable String id) {
    //		// call with service of payment for process payment operation
    //		var payId=UUID.randomUUID().toString();
    //		return service.pay(id,payId);
    //	}
    @PutMapping("/cancel-pay/{orderId}")
    Checkout cancelOrder(@PathVariable String orderId) {
        return service.cancelPay(orderId);
    }

    @GetMapping("/{id}")
    Checkout findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/find/cart/{id}")
    public Checkout findCartById(@PathVariable String id) {
        return service.findByCartId(id);
    }
    ;

    @GetMapping("/{id}/by-cart/{cartId}")
    public Checkout findCartByIdAndUserName(@PathVariable String id, @PathVariable String cartId) {
        return service.findByIdAndCartId(id, cartId);
    }
    ;

    @GetMapping("/status/{status}")
    public PagedResult<Checkout> findByStatus(
            @PathVariable CheckoutStatus status,
            @RequestParam(defaultValue = "0") Integer pageNumper,
            @RequestParam(defaultValue = "10") Integer sizePage) {
        return service.findByStatus(status, pageNumper, sizePage);
    }
    ;

    @GetMapping
    public PagedResult<Checkout> findAll(
            @RequestParam(defaultValue = "0") Integer pageNumper, @RequestParam(defaultValue = "10") Integer sizePage) {
        return service.findAll(pageNumper, sizePage);
    }
    ;
}
