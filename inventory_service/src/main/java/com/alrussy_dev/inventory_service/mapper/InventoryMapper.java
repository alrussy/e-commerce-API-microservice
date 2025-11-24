package com.alrussy_dev.inventory_service.mapper;

import com.alrussy_dev.inventory_service.client.SkuProduct;
import com.alrussy_dev.inventory_service.model.Inventory;
import com.alrussy_dev.inventory_service.model.dto.ActionForItemResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryResponse;
import com.alrussy_dev.inventory_service.model.enums.ActionType;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public InventoryResponse fromEntity(Inventory entity, SkuProduct skuProduct) {
        return new InventoryResponse(
                skuProduct,
                null,
                null,
                null,
                null,
                entity.getLineProducts() == null
                        ? null
                        : entity.getLineProducts().stream()
                                .map(lineProduct -> new ActionForItemResponse(
                                        lineProduct.getAction().getActionId(),
                                        lineProduct.getAction().getActionDate(),
                                        lineProduct.getQuantity(),
                                        lineProduct.getAction().getActionType(),
                                        lineProduct.getAction().getDescription()))
                                .toList());
    }

    public InventoryResponse fromEntityWithActions(Inventory entity, SkuProduct skuProduct) {
        var incoming = entity.getLineProducts().stream()
                .filter(q -> q.getAction().getActionType().equals(ActionType.ADD_STOCK))
                .mapToInt(q -> q.getQuantity())
                .sum();
        var outoing = entity.getLineProducts().stream()
                .filter(q -> q.getAction().getActionType().equals(ActionType.REMOVE_STOCK))
                .mapToInt(q -> q.getQuantity())
                .sum();

        return new InventoryResponse(
                skuProduct,
                entity.getOpeningStock(),
                incoming,
                outoing,
                entity.getOpeningStock() + incoming - outoing,
                entity.getLineProducts() == null
                        ? null
                        : entity.getLineProducts().stream()
                                .map(lineProduct -> {
                                    return new ActionForItemResponse(
                                            lineProduct.getAction().getActionId(),
                                            lineProduct.getAction().getActionDate(),
                                            lineProduct.getQuantity(),
                                            lineProduct.getAction().getActionType(),
                                            lineProduct.getAction().getDescription());
                                })
                                .toList());
    }

    public InventoryResponse fromEntityWithStock(Inventory entity, SkuProduct skuProduct) {
        var incoming = entity.getLineProducts().stream()
                .filter(q -> q.getAction().getActionType().equals(ActionType.ADD_STOCK))
                .mapToInt(q -> q.getQuantity())
                .sum();
        var outoing = entity.getLineProducts().stream()
                .filter(q -> q.getAction().getActionType().equals(ActionType.REMOVE_STOCK))
                .mapToInt(q -> q.getQuantity())
                .sum();

        return new InventoryResponse(skuProduct, null, null, null, entity.getOpeningStock() + incoming - outoing, null);
    }

    public InventoryResponse fromEntityWithActionsSummury(Inventory entity, SkuProduct skuProduct) {
        var incoming = entity.getLineProducts().stream()
                .filter(q -> q.getAction().getActionType().equals(ActionType.ADD_STOCK))
                .mapToInt(q -> q.getQuantity())
                .sum();
        var outoing = entity.getLineProducts().stream()
                .filter(q -> q.getAction().getActionType().equals(ActionType.REMOVE_STOCK))
                .mapToInt(q -> q.getQuantity())
                .sum();
        return new InventoryResponse(
                skuProduct,
                entity.getOpeningStock(),
                incoming,
                outoing,
                entity.getOpeningStock() + incoming - outoing,
                null);
    }
}
	
	
	
					

//
//	return new InventoryWithActionsResponse(t.toString(),openingStock,
//			incoming,outoing,openingStock+incoming-outoing,actions);
//	}
//
//			)
//	.orElse(new InventoryWithActionsResponse(skuCode,openingStock,
//			0,0	,openingStock,null));
//
// }
