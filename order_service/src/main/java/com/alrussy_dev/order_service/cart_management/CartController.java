package com.alrussy_dev.order_service.cart_management;

import com.alrussy_dev.order_service.cart_management.entity.Cart;
import com.alrussy_dev.order_service.cart_management.service.CartService;
import com.alrussy_dev.order_service.checkout_management.entity.Checkout;
import com.alrussy_dev.order_service.checkout_management.service.CheckoutService;
import com.alrussy_dev.order_service.common.CartStatus;
import com.alrussy_dev.order_service.common.PagedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CheckoutService checkoutService;

    //	@PostMapping("/new")
    //	public String openNewCart(@RequestParam String username) {
    //		return cartService.openNewCart(username);
    //	}
    //	@PutMapping("/checkout/{id}")
    //	public Cart checkout(@PathVariable String id) {
    //		var checkoutId= checkoutService.startCheckout(id).getId();
    //		return cartService.checkout(id,checkoutId);
    //	};

    //	@PutMapping("/add-product/{id}")
    //	public Cart addLineProduct(@PathVariable String id,@RequestBody LineProduct lineProduct) {
    //		return cartService.addLineProduct(id, lineProduct);
    //	};
    //	@PutMapping("/remove-product/{id}")
    //	public Cart removeLineProduct(@PathVariable String id,@RequestParam String skuCode) {
    //		return cartService.removeLineProduct(id, skuCode);
    //	};
    //	@DeleteMapping("/cancel/{id}")
    //	public Cart cancelCart(@PathVariable String id) {
    //		return cartService.cancelCart(id);
    //	};
    @GetMapping("/{id}")
    public Cart findCartById(@PathVariable String id) {
        return cartService.findCartById(id);
    }
    ;

    @GetMapping("/find/checkout/{id}")
    public Checkout findcheckoutById(@PathVariable String id) {
        return checkoutService.findById(id);
    }
    ;

    @GetMapping("/customer/{customerId}")
    public Cart findCartByCustomerId(@PathVariable String customerId) {
        return cartService.findCartByCustomrerId(customerId);
    }
    ;

    @GetMapping("/status/{status}")
    public PagedResult<Cart> findByStatus(
            @PathVariable CartStatus status,
            @RequestParam(defaultValue = "0") Integer pageNumper,
            @RequestParam(defaultValue = "10") Integer sizePage) {
        return cartService.findByStatus(status, pageNumper, sizePage);
    }
    ;

    @GetMapping
    public PagedResult<Cart> findAll(
            @RequestParam(defaultValue = "0") Integer pageNumper, @RequestParam(defaultValue = "10") Integer sizePage) {
        return cartService.findAll(pageNumper, sizePage);
    }
    ;
    //	@PutMapping("/change-quantity/{id}")
    //	public Cart changeQuantity(@PathVariable String id,@RequestBody LineProduct lineProduct) {
    //		return cartService.changeQuantity(id, lineProduct);
    //	};
}
