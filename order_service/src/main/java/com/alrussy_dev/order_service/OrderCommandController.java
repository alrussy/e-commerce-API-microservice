package com.alrussy_dev.order_service;

import com.alrussy_dev.order_service.OrderCommands.AddAddressCommand;
import com.alrussy_dev.order_service.OrderCommands.AddPaymentMethodCommand;
import com.alrussy_dev.order_service.OrderCommands.AddProductCommand;
import com.alrussy_dev.order_service.OrderCommands.ChangeQuantityCommand;
import com.alrussy_dev.order_service.OrderCommands.ConfirmOrderCommand;
import com.alrussy_dev.order_service.OrderCommands.CreateOrderCommand;
import com.alrussy_dev.order_service.OrderCommands.DeliveryOrderCommand;
import com.alrussy_dev.order_service.OrderCommands.OpenNewCartCommand;
import com.alrussy_dev.order_service.OrderCommands.RemoveProductCommand;
import com.alrussy_dev.order_service.OrderCommands.ShipOrderCommand;
import com.alrussy_dev.order_service.OrderCommands.StartCheckoutCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/command")
@RequiredArgsConstructor
public class OrderCommandController {

    private final OrderCommandHandler handler;

    @PostMapping("/new")
    public ResponseEntity<Boolean> openNewCart(@RequestBody OpenNewCartCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/add-product")
    public ResponseEntity<Boolean> addProduct(@RequestBody AddProductCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/remove-product")
    public ResponseEntity<Boolean> removeProduct(@RequestBody RemoveProductCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/change-quantity")
    public ResponseEntity<Boolean> changeQuantity(@RequestBody ChangeQuantityCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/start-checkout")
    public ResponseEntity<Boolean> startCheckout(@RequestBody StartCheckoutCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/add-address")
    public ResponseEntity<Boolean> addAddress(@RequestBody AddAddressCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/add-payment-method")
    public ResponseEntity<Boolean> addpaymentMethod(@RequestBody AddPaymentMethodCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/create-order")
    public ResponseEntity<Boolean> createOrder(@RequestBody CreateOrderCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/confirm-order")
    public ResponseEntity<Boolean> confirmOrder(@RequestBody ConfirmOrderCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/ship-order")
    public ResponseEntity<Boolean> shippOrder(@RequestBody ShipOrderCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @PostMapping("/delivery-order")
    public ResponseEntity<Boolean> deliveryOrder(@RequestBody DeliveryOrderCommand command) {
        return ResponseEntity.ok(handler.handle(command));
    }

    @GetMapping("/rebuild")
    public void rebuildAllEvents() {
        handler.rebuildAllEvents();
    }
}
