package com.alrussy_dev.notification_service.controller;

import com.alrussy_dev.notification_service.entity.EventType;
import com.alrussy_dev.notification_service.entity.NotificationOrderState;
import com.alrussy_dev.notification_service.entity.SummaryOrder;
import com.alrussy_dev.notification_service.service.NotifiOrderStateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {

    final NotifiOrderStateService service;

    @GetMapping
    public ResponseEntity<List<NotificationOrderState>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/no-sent")
    public ResponseEntity<List<NotificationOrderState>> findAllIsNotSent() {
        return ResponseEntity.ok(service.findAllIsNotSent());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationOrderState>> findByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(service.findByUserId(userId));
    }

    @PutMapping
    public ResponseEntity<String> ResendToEmails() {
        return ResponseEntity.ok(service.reSend());
    }

    @GetMapping("/test")
    public void test(@RequestParam String orderNumber, @RequestParam String userId) {
        service.sendAndSaveOrderState(new SummaryOrder(orderNumber, userId, EventType.CREATED));
    }
}
