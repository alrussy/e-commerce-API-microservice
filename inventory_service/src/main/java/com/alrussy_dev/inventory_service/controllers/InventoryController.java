package com.alrussy_dev.inventory_service.controllers;

import com.alrussy_dev.inventory_service.model.dto.ActionRequest;
import com.alrussy_dev.inventory_service.model.dto.InventoryResponse;
import com.alrussy_dev.inventory_service.model.dto.OpeningStock;
import com.alrussy_dev.inventory_service.model.dto.PagedResult;
import com.alrussy_dev.inventory_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/with-actions")
    public ResponseEntity<PagedResult<InventoryResponse>> findAllInventoryWithActions(
            @RequestParam(defaultValue = "1") Integer pageNumber) {
        return ResponseEntity.ok(service.findAllInventoryWithActions(pageNumber));
    }

    @GetMapping("/with-actions/summury")
    public ResponseEntity<PagedResult<InventoryResponse>> findAllInventoryActionsSummury(
            @RequestParam(defaultValue = "1") Integer pageNumber) {
        return ResponseEntity.ok(service.findAllInventoryWithActions(pageNumber));
    }

    @GetMapping
    public ResponseEntity<PagedResult<InventoryResponse>> findAllInventory(
            @RequestParam(defaultValue = "1") Integer pageNumber) {
        return ResponseEntity.ok(service.findAllInventoryWithStock(pageNumber));
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<InventoryResponse> findBySkuCode(@PathVariable String skuCode) {
        return ResponseEntity.ok(service.findBySkuCode(skuCode));
    }

    @PostMapping("/opening-stock")
    public ResponseEntity<OpeningStock> addOpeningStock(@RequestBody OpeningStock request) {
        System.out.println(request);
        return ResponseEntity.ok(service.addOpeningStock(request));
    }

    @PostMapping
    public ResponseEntity<String> action(@RequestBody ActionRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(service.action(request));
    }

    @PutMapping
    public ResponseEntity<String> editAction(@RequestBody ActionRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(service.editAction(request));
    }
    //    @GetMapping("/with-actions")
    //    public ResponseEntity<List<InventoryWithActionsResponse>> findAllInventoryWithActions() {
    //        return ResponseEntity.ok(service.findAllWithActions());
    //    }
    //    @GetMapping("/with-actions/{skuCode}")
    //    public ResponseEntity<InventoryWithActionsResponse> findAllInventoryWithActions(@PathVariable String skuCode)
    // {
    //        return ResponseEntity.ok(service.findAllWithActionBySkuCode(skuCode));
    //    }
    //    @GetMapping("/with-actions-summury")
    //    public ResponseEntity<List<InventoryWithActionsSummuryResponse>> findAllInventoryWithActionsSummury() {
    //        return ResponseEntity.ok(service.findAllWithActionSummury());
    //    }
    //    @GetMapping("/skuCode/with-actions-summury/{skuCode}")
    //    public ResponseEntity<InventoryWithActionsSummuryResponse> findAllInventoryWithActionsSummury(@PathVariable
    // String skuCode) {
    //        return ResponseEntity.ok(service.findAllWithActionSummuryBySkuCode(skuCode));
    //    }
    //
    //
    //    @GetMapping("/skuCode/{skuCode}")
    //    public ResponseEntity<InventoryOutActionsResponse> findBySkuCode(@PathVariable String skuCode) {
    //        return ResponseEntity.ok(service.findInventoryBySkuCode(skuCode));
    //    }
    //    @GetMapping("/skuCodes")
    //    public ResponseEntity<List<InventoryOutActionsResponse>> findBySkuCode(@RequestParam List<String> skuCodes) {
    //        return ResponseEntity.ok(service.findInventoryBySkuCodes(skuCodes));
    //    }
    //    @GetMapping("/actions/{skuCode}")
    //    public ResponseEntity<List<ActionForItemResponse>> findActionsBySkuCode(@PathVariable String skuCode) {
    //        return ResponseEntity.ok(service.findActionsBySkuCode(skuCode));
    //    }
    //
    //    @GetMapping("/actions/action/id")
    //    public ResponseEntity<ActionResponse> findByActionId(@RequestParam String actionId) {
    //        return ResponseEntity.ok(service.findByActionId(actionId));
    //    }
    //
    //    @GetMapping("/actions")
    //    public ResponseEntity<List<ActionResponse>> findAllActions() {
    //        return ResponseEntity.ok(service.findAllActions());
    //    }
    //
    //    @GetMapping("/actions/not-processed")
    //    public ResponseEntity<List<ActionResponse>> findByIsNotProcessed() {
    //        return ResponseEntity.ok(service.findByIsNotProcessed());
    //    }

    @PostMapping("/action/processed")
    public ResponseEntity<String> processedAction(@RequestParam("action-id") String actionId) {
        return ResponseEntity.ok(service.processeAction(actionId));
    }
}
