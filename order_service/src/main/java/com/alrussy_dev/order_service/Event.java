package com.alrussy_dev.order_service;

import com.alrussy_dev.order_service.common.Address;
import com.alrussy_dev.order_service.common.CartStatus;
import com.alrussy_dev.order_service.common.CheckoutStatus;
import com.alrussy_dev.order_service.common.LineProduct;
import com.alrussy_dev.order_service.common.OrderStatus;
import com.alrussy_dev.order_service.common.PaymentMethod;
import java.time.LocalDateTime;
import java.util.Set;

public sealed interface Event {
    public String topic();

    public LocalDateTime timestamp();

    public record OpenedNewCartEvent(String aggregateId, String customerId, CartStatus status) implements Event {
        @Override
        public String topic() {
            return "Opened-New-Card";
        }

        @Override
        public LocalDateTime timestamp() {
            // TODO Auto-generated method stub
            return LocalDateTime.now();
        }
    }

    public record CreatedOrderEvent(String aggregateId, OrderStatus orderStatus, Set<LineProduct> lineProducts)
            implements Event {
        @Override
        public String topic() {
            return "Created-Order";
        }

        @Override
        public LocalDateTime timestamp() {
            // TODO Auto-generated method stub
            return LocalDateTime.now();
        }
    }

    public record AddedProductEvent(String aggregateId, LineProduct lineProduct) implements Event {
        @Override
        public String topic() {
            return "Added-Product-To-Cart";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record RemovedProductEvent(String aggregateId, String skuCode) implements Event {
        @Override
        public String topic() {
            return "Removed-Product-From-Cart";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record ChangedQuantityEvent(String aggregateId, Integer quantity, String skuCode) implements Event {
        @Override
        public String topic() {
            return "Changed-Quantity";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record StartedCheckoutEvent(String aggregateId, CheckoutStatus checkoutStatus) implements Event {
        @Override
        public String topic() {
            return "Started-Checkout";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record AddedAddressEvent(String aggregateId, Address address) implements Event {
        @Override
        public String topic() {
            return "Added-Address";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record AddedShippingFeeAndTaxFee(String aggregateId, Double shippingFee, Double taxFee) implements Event {
        @Override
        public String topic() {
            return " Added-Shipping-Fee-And-TaxFee";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record AddedPaymentMethodEvent(String aggregateId, PaymentMethod paymentMethod) implements Event {
        @Override
        public String topic() {
            return "Added-Payment-Method";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record ConfirmedOrderEvent(String aggregateId, OrderStatus orderStatus) implements Event {
        @Override
        public String topic() {
            return "Confirmed-Order";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record ShippedOrderEvent(String aggregateId, OrderStatus orderStatus) implements Event {
        @Override
        public String topic() {
            return "Shipped-Order";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }

    public record DeliveredOrderEven(String aggregateId, OrderStatus orderStatus) implements Event {
        @Override
        public String topic() {
            return "Delivered-Order";
        }

        @Override
        public LocalDateTime timestamp() {
            return LocalDateTime.now();
        }
    }
}
