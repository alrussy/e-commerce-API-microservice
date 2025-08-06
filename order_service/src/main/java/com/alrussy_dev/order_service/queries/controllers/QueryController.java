package com.alrussy_dev.order_service.queries.controllers;

import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import com.alrussy_dev.order_service.queries.model.dto.PagedResult;
import com.alrussy_dev.order_service.queries.services.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders/Query")
@RequiredArgsConstructor
@CrossOrigin("*")
public class QueryController {
    private final OrderService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> findByUser(@PathVariable String userId) {
        return ResponseEntity.ok(service.findByUser(userId));
    }

    @GetMapping
    public ResponseEntity<PagedResult<OrderResponse>> findAll(
            @RequestParam(defaultValue = "0", name = "pageNumber") Integer pageNumber) {
        return ResponseEntity.ok(service.findAll(pageNumber));
    }

    @GetMapping("/admin/{orderNumber}")
    public ResponseEntity<OrderResponse> findById(@PathVariable String orderNumber) {
        return ResponseEntity.ok(service.findByOrderNumber(orderNumber));
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> countByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(service.countByUser(userId));
    }
}
