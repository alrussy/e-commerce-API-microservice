package com.alrussy_dev.order_service.queries.controllers;

import com.alrussy_dev.order_service.common.OrderStatus;
import com.alrussy_dev.order_service.common.PagedResult;
import com.alrussy_dev.order_service.common.PaymentMethod;
import com.alrussy_dev.order_service.queries.model.dto.Filter;
import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import com.alrussy_dev.order_service.queries.services.OrderService;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/query")
@RequiredArgsConstructor
@CrossOrigin("*")
public class QueryController {
    private final OrderService service;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> findByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(service.findByCustomerId(customerId));
    }

    @GetMapping("/filter/pages")
    public ResponseEntity<PagedResult<OrderResponse>> find(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "orderNumber") String[] sortedby,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) Long orderNumber,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false, name = "fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDateTime fromDate,
            @RequestParam(required = false, name = "toDate") @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDateTime toDate,
            @RequestParam(required = false) PaymentMethod paymentMethod,
            @RequestParam(required = false) Double amountGreateThan,
            @RequestParam(required = false) Double amountGreateThanOrEqual,
            @RequestParam(required = false) Double amountLessThanOrEqual,
            @RequestParam(required = false) Double amountLessThan,
            @RequestParam(required = false) Double amountEqual,
            @RequestParam(required = false) String skuCode)
            throws ParseException {

        Filter filter = new Filter(
                pageNumber,
                sortedby,
                direction,
                orderNumber,
                customerId,
                status,
                fromDate != null ? fromDate : null,
                toDate,
                paymentMethod,
                amountGreateThan,
                amountGreateThanOrEqual,
                amountLessThanOrEqual,
                amountLessThan,
                amountEqual,
                skuCode);

        return ResponseEntity.ok(service.find(filter));
    }

    @GetMapping
    public ResponseEntity<PagedResult<OrderResponse>> findAll(
            @RequestParam(defaultValue = "1", name = "pageNumber") Integer pageNumber) {
        return ResponseEntity.ok(service.findAll(pageNumber));
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderResponse> findById(@PathVariable String orderNumber) {
        return ResponseEntity.ok(service.findByOrderNumber(orderNumber));
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> countByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(service.countByUser(userId));
    }
}
