package com.alrussy_dev.inventory_service.controllers;

import com.alrussy_dev.inventory_service.model.dto.ActionForItemResponse;
import com.alrussy_dev.inventory_service.model.dto.ActionRequest;
import com.alrussy_dev.inventory_service.model.dto.ActionResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryOutActionsResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryWithActionsResponse;
import com.alrussy_dev.inventory_service.model.enums.ActionType;
import com.alrussy_dev.inventory_service.services.InventoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InventoryController {

    private final InventoryService service;

    @GetMapping
    public ResponseEntity<List<InventoryOutActionsResponse>> findAllInventoryOutActions() {
        return ResponseEntity.ok(service.findAllInventoryOutActions());
    }

    @PostMapping
    public ResponseEntity<String> stock(@RequestBody ActionRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(service.action(request.actionId(), ActionType.ADD_STOCK, request.lineProduct()));
    }

    @GetMapping("/with-actions")
    public ResponseEntity<List<InventoryWithActionsResponse>> findAllInventoryWithActions() {
        return ResponseEntity.ok(service.findAllWithAction());
    }

    @GetMapping("/skuCode/{skuCode}")
    public ResponseEntity<InventoryOutActionsResponse> findBySkuCode(@PathVariable String skuCode) {
        return ResponseEntity.ok(service.findInventoryBySkuCode(skuCode));
    }

    @GetMapping("/actions/{skuCode}")
    public ResponseEntity<List<ActionForItemResponse>> findActionsBySkuCode(@PathVariable String skuCode) {
        return ResponseEntity.ok(service.findActionsBySkuCode(skuCode));
    }

    @GetMapping("/actions/action/id")
    public ResponseEntity<ActionResponse> findByActionId(@RequestParam String actionId) {
        return ResponseEntity.ok(service.findByActionId(actionId));
    }

    @GetMapping("/actions")
    public ResponseEntity<List<ActionResponse>> findAllActions() {
        return ResponseEntity.ok(service.findAllActions());
    }

    @GetMapping("/actions/not-processed")
    public ResponseEntity<List<ActionResponse>> findByIsNotProcessed() {
        return ResponseEntity.ok(service.findByIsNotProcessed());
    }

    @PostMapping("/action/processed")
    public ResponseEntity<String> processedAction(@RequestParam("action-id") String actionId) {
        return ResponseEntity.ok(service.processeAction(actionId));
    }
}
