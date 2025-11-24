package com.alrussy_dev.order_service;

import com.alrussy_dev.order_service.Event.AddedAddressEvent;
import com.alrussy_dev.order_service.Event.AddedPaymentMethodEvent;
import com.alrussy_dev.order_service.Event.AddedProductEvent;
import com.alrussy_dev.order_service.Event.AddedShippingFeeAndTaxFee;
import com.alrussy_dev.order_service.Event.ChangedQuantityEvent;
import com.alrussy_dev.order_service.Event.ConfirmedOrderEvent;
import com.alrussy_dev.order_service.Event.CreatedOrderEvent;
import com.alrussy_dev.order_service.Event.DeliveredOrderEven;
import com.alrussy_dev.order_service.Event.OpenedNewCartEvent;
import com.alrussy_dev.order_service.Event.RemovedProductEvent;
import com.alrussy_dev.order_service.Event.ShippedOrderEvent;
import com.alrussy_dev.order_service.Event.StartedCheckoutEvent;
import com.alrussy_dev.order_service.common.Address;
import com.alrussy_dev.order_service.common.CartStatus;
import com.alrussy_dev.order_service.common.CheckoutStatus;
import com.alrussy_dev.order_service.common.LineProduct;
import com.alrussy_dev.order_service.common.OrderStatus;
import com.alrussy_dev.order_service.common.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderAggregate {
    private String aggregateId;
    private LocalDateTime timestamp;
    private String customerId;
    private CheckoutStatus checkoutStatus;
    private CartStatus cartStatus;
    private OrderStatus orderStatus;
    private Address address;
    private PaymentMethod paymentMethod;
    private Set<LineProduct> lineProducts = new HashSet<LineProduct>();
    private Double shippingFee;
    private Double taxFee;
    private String topic;
    private final List<Event> uncommittedEvents = new ArrayList<>();

    // Constructor to rebuild state from history
    public OrderAggregate(String aggregateId, List<Event> history) {
        System.out.println("event : " + history);
        this.aggregateId = aggregateId;
        history.forEach(this::apply);
    }

    public void openNewCart(@NotBlank String customerId) {
        if (this.cartStatus != null && this.cartStatus.equals(CartStatus.OPENED)) {
            throw new RuntimeException("Customer Have to Cart Opened");
        }
        var event = new OpenedNewCartEvent(this.aggregateId, customerId, CartStatus.OPENED);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void addProduct(@NotNull LineProduct lineProduct) {
        if (!this.cartStatus.equals(CartStatus.OPENED)) {
            throw new RuntimeException("Customer Not Have To Cart Opened");
        }
        if (this.lineProducts.stream()
                .filter(t -> t.getSkuCode().equals(lineProduct.getSkuCode()))
                .findFirst()
                .isPresent()) {
            throw new RuntimeException("The Product Is Found In Cart");
        }
        System.out.println(this.lineProducts.toString());

        var event = new AddedProductEvent(this.aggregateId, lineProduct);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void removeProduct(@NotNull String skuCode) {
        if (!this.cartStatus.equals(CartStatus.OPENED)) {
            throw new RuntimeException("Customer Not Have To Cart Opened");
        }

        if (!this.lineProducts.stream()
                .filter(t -> t.getSkuCode().equals(skuCode))
                .findFirst()
                .isPresent()) {
            throw new RuntimeException("The Product Is Not Found In Cart");
        }

        var event = new RemovedProductEvent(this.aggregateId, skuCode);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void changeQuantity(@Min(value = 1) Integer quantity, @NotNull String skuCode) {
        if (!this.cartStatus.equals(CartStatus.OPENED)) {
            throw new RuntimeException("Customer Not Have To Cart Opened");
        }

        if (!this.lineProducts.stream()
                .filter(t -> t.getSkuCode().equals(skuCode))
                .findFirst()
                .isPresent()) {
            throw new RuntimeException("The Product Is Not Found In Cart");
        }

        var event = new ChangedQuantityEvent(this.aggregateId, quantity, skuCode);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void startCheckout() {
        if (!this.cartStatus.equals(CartStatus.OPENED)) {
            return;
        }

        if (this.lineProducts == null || this.lineProducts.isEmpty()) {
            throw new RuntimeException("lineProducts is Undifind,You Must Add At Least One Product.");
        }
        var event = new StartedCheckoutEvent(this.aggregateId, CheckoutStatus.STARTED);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void addAddress(@NotNull Address address) {
        if (this.checkoutStatus == null || !this.checkoutStatus.equals(CheckoutStatus.STARTED)) {
            throw new RuntimeException("Checkout Out Started ,You Must Be Start Checkout");
        }
        var event = new Event.AddedAddressEvent(this.aggregateId, address);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void addPaymentMethod(@NotBlank PaymentMethod paymentMethod) {
        if (this.checkoutStatus == null || !this.checkoutStatus.equals(CheckoutStatus.STARTED)) {
            return;
        }
        var event = new Event.AddedPaymentMethodEvent(this.aggregateId, paymentMethod);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void addPaymentMethod(@NotBlank Double shippingFee, Double taxFee) {
        if (this.checkoutStatus == null || !this.checkoutStatus.equals(CheckoutStatus.STARTED)) {
            return;
        }
        var event = new Event.AddedShippingFeeAndTaxFee(this.aggregateId, shippingFee, taxFee);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void createOrder() {
        if (this.address == null || this.paymentMethod == null) {
            throw new RuntimeException(
                    "An Order Cannot Be Created. The Address Or Payment Method Must Be Not Null Or Empty.");
        }
        var event = new Event.CreatedOrderEvent(this.aggregateId, OrderStatus.CREATED, this.lineProducts);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void confirmOrder() {
        if (this.orderStatus != null && !this.orderStatus.equals(OrderStatus.CREATED)) {
            throw new RuntimeException("An Order Cannot Be Confirmd. You Must by  Create Order.");
        }
        var event = new Event.ConfirmedOrderEvent(this.aggregateId, OrderStatus.CONFIRMED);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void shipOrder() {
        if (this.orderStatus != null && !this.orderStatus.equals(OrderStatus.CONFIRMED)) {
            throw new RuntimeException("An Order Cannot Be Shipped. You Must by  confirm Order.");
        }
        var event = new Event.ShippedOrderEvent(this.aggregateId, OrderStatus.SHIPPED);
        uncommittedEvents.add(event);
        apply(event);
    }

    public void deliveryOrder() {
        if (this.orderStatus != null && !this.orderStatus.equals(OrderStatus.SHIPPED)) {
            throw new RuntimeException("An Order Cannot Be Delivered. You Must by  ship Order.");
        }
        var event = new Event.DeliveredOrderEven(this.aggregateId, OrderStatus.DELIVERED);
        uncommittedEvents.add(event);
        apply(event);
    }
    // Event application logic
    public void apply(Event event) {
        System.out.println("event : " + event);
        this.timestamp = event.timestamp();
        switch (event) {
            case OpenedNewCartEvent e -> {
                this.customerId = e.customerId();
                this.cartStatus = e.status();
                this.address = null;
                this.checkoutStatus = null;
                this.orderStatus = null;
                this.paymentMethod = null;
            }
            case AddedProductEvent e -> {
                this.lineProducts.add(e.lineProduct());
            }

            case RemovedProductEvent e -> {
                var lineProduct = this.lineProducts.stream()
                        .filter(line -> line.getSkuCode().equals(e.skuCode()))
                        .findFirst();
                if (lineProduct.isPresent()) {
                    this.lineProducts.remove(lineProduct.get());
                }
            }
            case ChangedQuantityEvent e -> {
                var lineProduct = this.lineProducts.stream()
                        .filter(line -> line.getSkuCode().equals(e.skuCode()))
                        .findFirst()
                        .get();
                lineProduct.setQuantity(e.quantity());
            }
            case StartedCheckoutEvent e -> {
                this.checkoutStatus = CheckoutStatus.STARTED;
            }
            case AddedAddressEvent e -> {
                this.address = e.address();
            }
            case AddedPaymentMethodEvent e -> {
                this.paymentMethod = e.paymentMethod();
            }
            case AddedShippingFeeAndTaxFee e -> {
                this.shippingFee = e.shippingFee();
                this.taxFee = e.taxFee();
            }
            case CreatedOrderEvent e -> {
                this.orderStatus = OrderStatus.CREATED;
                this.cartStatus = CartStatus.CLOSED;
                this.checkoutStatus = CheckoutStatus.COMPLETED;
            }
            case ShippedOrderEvent e -> {
                this.orderStatus = OrderStatus.SHIPPED;
                this.cartStatus = CartStatus.CLOSED;
                this.checkoutStatus = CheckoutStatus.COMPLETED;
            }

            case DeliveredOrderEven e -> {
                this.orderStatus = OrderStatus.DELIVERED;
                this.cartStatus = CartStatus.CLOSED;
                this.checkoutStatus = CheckoutStatus.COMPLETED;
            }

            case ConfirmedOrderEvent e -> {
                this.orderStatus = OrderStatus.CONFIRMED;
                this.cartStatus = CartStatus.CLOSED;
                this.checkoutStatus = CheckoutStatus.COMPLETED;
            }
        }

        this.topic = event.topic();
    }

    public List<Event> getUncommittedEvents() {
        return uncommittedEvents;
    }
}
