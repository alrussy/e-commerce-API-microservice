package com.alrussy_dev.order_service.checkout_management.service;

import com.alrussy_dev.order_service.Event.AddedAddressEvent;
import com.alrussy_dev.order_service.Event.AddedPaymentMethodEvent;
import com.alrussy_dev.order_service.Event.CreatedOrderEvent;
import com.alrussy_dev.order_service.Event.StartedCheckoutEvent;
import com.alrussy_dev.order_service.checkout_management.entity.Checkout;

public interface CheckoutService extends BaseService<String, Checkout, Checkout> {

    void startCheckout(StartedCheckoutEvent event);

    void setAddress(AddedAddressEvent event);

    void setPaymentMethod(AddedPaymentMethodEvent event);

    void createdOrder(CreatedOrderEvent event);
}
