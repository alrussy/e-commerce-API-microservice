package com.alrussy_dev.inventory_service.services;

import com.alrussy_dev.inventory_service.model.ActionInventory;
import com.alrussy_dev.inventory_service.model.InventoryId;
import com.alrussy_dev.inventory_service.model.LineProduct;
import com.alrussy_dev.inventory_service.model.dto.ActionForItemResponse;
import com.alrussy_dev.inventory_service.model.dto.ActionResponse;
import com.alrussy_dev.inventory_service.model.dto.EventRequest;
import com.alrussy_dev.inventory_service.model.dto.InventoryOutActionsResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryWithActionResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryWithActionsResponse;
import com.alrussy_dev.inventory_service.model.dto.LineProductOfInventory;
import com.alrussy_dev.inventory_service.model.enums.ActionType;
import com.alrussy_dev.inventory_service.publisher.OrderProcessedEventPublisher;
import com.alrussy_dev.inventory_service.repository.ActionInventoryRepository;
import com.alrussy_dev.inventory_service.repository.InventoryRepository;
import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ActionInventoryRepository irepository;
    private final InventoryRepository inventoryRepository;
    private final OrderProcessedEventPublisher publish;

    @Transactional
    public String action(String actionId, ActionType actionType, List<LineProductOfInventory> inventories) {

        var action = ActionInventory.builder()
                .actionDate(LocalDateTime.now())
                .actionId(actionId)
                .actionType(actionType)
                .isProcessed(false)
                .isPublished(false)
                .lineProducts(inventories.stream()
                        .map(t -> new LineProduct(new InventoryId(actionId, t.skuCode()), t.quantity(), null))
                        .toList())
                .build();
        return irepository.save(action).getActionId();
    }

    public String processeAction(String actionId) {
        ActionInventory action = irepository.findById(actionId).orElseThrow(() -> new RuntimeException("action"));
        action.setIsProcessed(true);
        return irepository.save(action).getActionId();
    }

    public List<InventoryWithActionsResponse> findAllWithAction() {

        var lineProducts = irepository.findAll().stream()
                .flatMap(action -> action.getLineProducts().stream()
                        .map(t -> new InventoryWithActionResponse(
                                t.getId().skuCode(),
                                t.getQuantity(),
                                new ActionForItemResponse(
                                        action.getActionId(),
                                        action.getActionDate(),
                                        t.getQuantity(),
                                        action.getActionType(),
                                        action.getDescription()))))
                .toList();

        Map<String, List<InventoryWithActionResponse>> mapInventory =
                lineProducts.stream().collect(Collectors.groupingBy(t -> t.skuCode()));
        List<InventoryWithActionsResponse> inventory = mapInventory.keySet().stream()
                .map(t -> new InventoryWithActionsResponse(
                        t.toString(),
                        mapInventory.get(t).stream()
                                .mapToInt(q -> q.action().type().equals(ActionType.REMOVE_STOCK)
                                        ? q.stockQuantity() * -1
                                        : q.stockQuantity())
                                .sum(),
                        mapInventory.get(t).stream().map(x -> x.action()).toList()))
                .toList();

        return inventory;
    }

    public ActionResponse findByActionId(String actionId) {
        var action =
                irepository.findByActionId(actionId).orElseThrow(() -> new RuntimeException("action is not found"));

        return new ActionResponse(
                action.getActionId(),
                action.getActionDate(),
                action.getActionType(),
                action.getDescription(),
                action.getLineProducts().stream()
                        .map(l -> new LineProductOfInventory(l.getId().skuCode(), l.getQuantity()))
                        .toList());
    }

    public List<ActionResponse> findByIsNotProcessed() {
        List<ActionInventory> actions = irepository.findByIsProcessedAndIsPublished(
                false, false, Sort.by("actionDate").descending());
        return actions.stream()
                .map(action -> new ActionResponse(
                        action.getActionId(),
                        action.getActionDate(),
                        action.getActionType(),
                        action.getDescription(),
                        action.getLineProducts().stream()
                                .map(l -> new LineProductOfInventory(l.getId().skuCode(), l.getQuantity()))
                                .toList()))
                .toList();
    }

    public List<ActionResponse> findAllActions() {
        List<ActionInventory> actions = irepository.findAll();
        return actions.stream()
                .map(action -> new ActionResponse(
                        action.getActionId(),
                        action.getActionDate(),
                        action.getActionType(),
                        action.getDescription(),
                        action.getLineProducts().stream()
                                .map(l -> new LineProductOfInventory(l.getId().skuCode(), l.getQuantity()))
                                .toList()))
                .toList();
    }

    public List<InventoryOutActionsResponse> findAllInventoryOutActions() {
        var lineProducts = irepository.findAll().stream()
                .flatMap(action -> action.getLineProducts().stream()
                        .map(t -> new InventoryWithActionResponse(
                                t.getId().skuCode(),
                                t.getQuantity(),
                                new ActionForItemResponse(
                                        action.getActionId(),
                                        action.getActionDate(),
                                        t.getQuantity(),
                                        action.getActionType(),
                                        action.getDescription()))))
                .toList();

        Map<String, List<InventoryWithActionResponse>> mapInventory =
                lineProducts.stream().collect(Collectors.groupingBy(t -> t.skuCode()));

        List<InventoryOutActionsResponse> inventory = mapInventory.keySet().stream()
                .map(t -> new InventoryOutActionsResponse(
                        t.toString(),
                        mapInventory.get(t).stream()
                                .mapToInt(q -> q.action().type().equals(ActionType.REMOVE_STOCK)
                                        ? q.stockQuantity() * -1
                                        : q.stockQuantity())
                                .sum()))
                .toList();

        return inventory;
    }

    public List<ActionForItemResponse> findActionsBySkuCode(String skuCode) {
        List<ActionForItemResponse> act = irepository.findByLineProductsIdSkuCode(skuCode).stream()
                .map(t -> new ActionForItemResponse(
                        t.getActionId(),
                        t.getActionDate(),
                        t.getLineProducts().stream()
                                .filter(l -> l.getId().skuCode().equals(skuCode))
                                .findFirst()
                                .get()
                                .getQuantity(),
                        t.getActionType(),
                        t.getDescription()))
                .toList();

        return act;
    }

    public InventoryOutActionsResponse findInventoryBySkuCode(String skuCode) {
        int quantity = irepository.findByLineProductsIdSkuCode(skuCode).stream()
                .flatMap(t -> t.getLineProducts().stream())
                .filter(l -> l.getId().skuCode().equals(skuCode))
                .mapToInt(q -> q.getQuantity())
                .sum();
        return new InventoryOutActionsResponse(skuCode, quantity);
    }

    public void eventPublishing() {
        List<ActionInventory> actions = irepository.findByIsProcessedAndIsPublished(
                true, false, Sort.by("actionDate").descending());
        actions.stream().forEachOrdered(t -> {
            if (t.getActionType().equals(ActionType.REMOVE_STOCK)) {
                publish.publish(new EventRequest(
                        null,
                        t.getActionId(),
                        t.getLineProducts().stream()
                                .map(l -> new LineProductOfInventory(l.getId().skuCode(), l.getQuantity()))
                                .toList()));
                t.setIsPublished(true);
                irepository.save(t);
            }
        });
    }

    private boolean checkQuntity(List<LineProductOfInventory> lineProducts) {

        return lineProducts.stream()
                .anyMatch(t -> findInventoryBySkuCode(t.skuCode()).stockQuantity() - t.quantity() < 0);
    }
}
