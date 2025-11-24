package com.alrussy.product_service.client.inventory;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class InventoryService {

    private RestClient client;

    public InventoryService(RestClient client) {
        this.client = client;
        // TODO Auto-generated constructor stub
    }

    public InventoryOutActions getInventoruOutActions(String skuCode) {
        var inventory = client.get().uri("/" + skuCode).retrieve().toEntity(InventoryOutActions.class);
        InventoryOutActions inventoryOutActions = inventory.getBody();
        return inventoryOutActions;
    }

    public Inventory addOpeningStock(Inventory request) {
        System.out.println(request);
        var res = client.post().uri("/opening-stock").body(request).retrieve().toEntity(Inventory.class);
        Inventory inventory = res.getBody();
        return inventory;
    }

    public InventoryWithActionsSummuryResponse getInventoryWithActionsSummury(String skuCode) {
        var inventory = client.get()
                .uri("/with-actions-summury/" + skuCode)
                .retrieve()
                .toEntity(InventoryWithActionsSummuryResponse.class);
        InventoryWithActionsSummuryResponse inventoryOutActions = inventory.getBody();
        System.out.println("inventory:" + skuCode);
        return inventoryOutActions;
    }
}
