package com.alrussy_dev.order_service;

import com.alrussy_dev.order_service.common.Address;
import com.alrussy_dev.order_service.common.LineProduct;
import com.alrussy_dev.order_service.common.PaymentMethod;

public sealed interface OrderCommands {

    public record OpenNewCartCommand(String customerId) implements OrderCommands {}

    public record AddProductCommand(String aggregateId, LineProduct lineProduct) implements OrderCommands {}

    public record RemoveProductCommand(String aggregateId, String skuCode) implements OrderCommands {}

    public record ChangeQuantityCommand(String aggregateId, Integer quantity, String skuCode)
            implements OrderCommands {}

    public record StartCheckoutCommand(String aggregateId) implements OrderCommands {}

    public record AddAddressCommand(String aggregateId, Address address) implements OrderCommands {}

    public record AddPaymentMethodCommand(String aggregateId, PaymentMethod paymentMethod) implements OrderCommands {}

    public record AddShippingFeeAndTaxFeeCommand(String aggregateId, Double shippingFee, Double taxFee)
            implements OrderCommands {}

    public record CreateOrderCommand(String aggregateId) implements OrderCommands {}

    public record ConfirmOrderCommand(String aggregateId) implements OrderCommands {}

    public record ShipOrderCommand(String aggregateId) implements OrderCommands {}

    public record DeliveryOrderCommand(String aggregateId) implements OrderCommands {}
}
