package com.alrussy_dev.procurement_service.client;

import com.alrussy_dev.procurement_service.dto.LineProductRequest;
import com.alrussy_dev.procurement_service.entity.LineProduct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final RestClient client;

    public String addStock(List<LineProduct> lineProducts, String invoiceNumber) {
        List<LineProductOfInventory> lineProductOfInventory = lineProducts.stream()
                .map(line -> new LineProductOfInventory(line.getSkuCode(), line.getQuantity()))
                .toList();
        ActionRequest actionRequest =
                new ActionRequest(invoiceNumber, ActionType.ADD_STOCK, "Procurement Invoice", lineProductOfInventory);
        return client.post()
                .uri("/inventory")
                .body(actionRequest)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }

    public String editAction(List<LineProductRequest> lineProducts, String invoiceNumber) {
        List<LineProductOfInventory> lineProductOfInventory = lineProducts.stream()
                .map(line -> new LineProductOfInventory(line.skuCode(), line.quantity()))
                .toList();
        ActionRequest actionRequest =
                new ActionRequest(invoiceNumber, ActionType.ADD_STOCK, "Procurement Invoice", lineProductOfInventory);
        return client.put()
                .uri("/inventory")
                .body(actionRequest)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }

    public String removeStock(List<LineProduct> lineProducts, String returnNumber) {
        List<LineProductOfInventory> lineProductOfInventory = lineProducts.stream()
                .map(line -> new LineProductOfInventory(line.getSkuCode(), line.getQuantity()))
                .toList();
        ActionRequest actionRequest =
                new ActionRequest(returnNumber, ActionType.REMOVE_STOCK, "Procurement Invoice", lineProductOfInventory);
        return client.post()
                .uri("/inventory")
                .body(actionRequest)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }
}
