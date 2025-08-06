package com.alrussy_dev.order_service.commands.controller;

import com.alrussy_dev.order_service.commands.service.OrderEventService;
import com.alrussy_dev.order_service.queries.model.dto.OrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders/command")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class CommandController {

    private final OrderEventService createNewOrderService;

    @PostMapping("/create")
    private ResponseEntity<String> palceOrder(@RequestBody @Valid OrderRequest request) {
        log.info("order request:{}", request);
        return ResponseEntity.ok(createNewOrderService.palceOrder(request));
    }
    //
    //    @PostMapping("/add-event")
    //    private ResponseEntity<String> addOrderEvent(@RequestBody  OrderProcessedEvent request) throws
    // JsonMappingException, JsonProcessingException {
    //        log.info("order request:{}", request);
    //
    //        return ResponseEntity.ok(createNewOrderService.handle(request));
    //    }
    // }
}
