package com.alrussy_dev.order_service.cart_management.service;

import com.alrussy_dev.order_service.Event.AddedProductEvent;
import com.alrussy_dev.order_service.Event.ChangedQuantityEvent;
import com.alrussy_dev.order_service.Event.CreatedOrderEvent;
import com.alrussy_dev.order_service.Event.OpenedNewCartEvent;
import com.alrussy_dev.order_service.Event.RemovedProductEvent;
import com.alrussy_dev.order_service.cart_management.entity.Cart;

public interface CartService extends BaseService<String, Cart, Cart> {

    void openNewCart(OpenedNewCartEvent event);

    void addLineProduct(AddedProductEvent event);

    void removeLineProduct(RemovedProductEvent event);

    void changeQuantity(ChangedQuantityEvent event);

    void closeCart(CreatedOrderEvent event);
}
