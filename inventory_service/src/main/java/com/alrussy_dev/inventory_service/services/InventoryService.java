package com.alrussy_dev.inventory_service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alrussy_dev.inventory_service.client.ProductService;
import com.alrussy_dev.inventory_service.mapper.InventoryMapper;
import com.alrussy_dev.inventory_service.mapper.PageMapper;
import com.alrussy_dev.inventory_service.model.ActionInventory;
import com.alrussy_dev.inventory_service.model.Inventory;
import com.alrussy_dev.inventory_service.model.InventoryId;
import com.alrussy_dev.inventory_service.model.LineProduct;
import com.alrussy_dev.inventory_service.model.dto.ActionForItemResponse;
import com.alrussy_dev.inventory_service.model.dto.ActionRequest;
import com.alrussy_dev.inventory_service.model.dto.ActionResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryOutActionsResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryWithActionResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryWithActionsResponse;
import com.alrussy_dev.inventory_service.model.dto.InventoryWithActionsSummuryResponse;
import com.alrussy_dev.inventory_service.model.dto.LineProductOfInventory;
import com.alrussy_dev.inventory_service.model.dto.OpeningStock;
import com.alrussy_dev.inventory_service.model.dto.PagedResult;
import com.alrussy_dev.inventory_service.model.enums.ActionType;
import com.alrussy_dev.inventory_service.repository.ActionInventoryRepository;
import com.alrussy_dev.inventory_service.repository.InventoryRepository;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

	private final ActionInventoryRepository irepository;
	private final InventoryRepository inventoryRepository;
	private final InventoryMapper mapper;
	private final ProductService productService;
	private final PageMapper<InventoryResponse> pageMapper;
	
	public PagedResult<InventoryResponse> findAllInventoryWithActions(Integer pageNumber) {
		
        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);

		return pageMapper.toPageResponse(
				inventoryRepository.findAll(page).map(inventory->{
			
			var skuProduct=  productService.getProducts(inventory.getSkuCode());
		return  	mapper.fromEntityWithActions(inventory,skuProduct);
		}));		
	}
	
	
	public PagedResult<InventoryResponse>findAllInventoryWithStock(Integer pageNumber) {
        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);

    	return pageMapper.toPageResponse(
				inventoryRepository.findAll(page).map(inventory->{
			
			var skuProduct=  productService.getProducts(inventory.getSkuCode());
		return  	mapper.fromEntityWithStock(inventory,skuProduct);
		}));		
		
	}
	
	public PagedResult<InventoryResponse>findAllInventoryWithActionSummury(Integer pageNumber) {
        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);

    	return pageMapper.toPageResponse(
				inventoryRepository.findAll(page).map(inventory->{
			
			var skuProduct=  productService.getProducts(inventory.getSkuCode());
		return  	mapper.fromEntityWithStock(inventory,skuProduct);
		}));		
		
	}
	public InventoryResponse findBySkuCode(String skuCode ) {
		var skuProduct=  productService.getProducts(skuCode);
		return mapper.fromEntityWithActions(inventoryRepository.findById(skuCode).orElseThrow(()-> new RuntimeException("Sku " +skuCode + "Not Found")),skuProduct);
		
	}
	
	public Inventory findInventoryById(String skuCode) {
		return inventoryRepository.findById(skuCode).orElseThrow(()-> new RuntimeException("Sku " +skuCode + "Not Found"));
	}
	
	
	
	@Transactional
	public String action(ActionRequest actionRequest) {
		
		if(actionRequest.type().equals(ActionType.PROCESS)) {
			return processeAction(actionRequest.actionId());
		}
		
		if(actionRequest.type().equals(ActionType.EDIET_STOCK)) {
			return editAction(actionRequest);
		}
		
		var lineProducts = actionRequest.lineProducts().stream().map(line -> {
			if (inventoryRepository.findById(line.skuCode()).isPresent())
				return new LineProduct(new InventoryId(actionRequest.actionId(), line.skuCode()), line.quantity(), null,null);
			else
				throw new RuntimeException("Sku " + line.skuCode() + "Not Found");
		}).collect(Collectors.toSet())
				

				
				;
		var action = ActionInventory.builder().actionDate(LocalDateTime.now()).actionId(actionRequest.actionId()).actionType(actionRequest.type())
				.isProcessed(false).isPublished(false).lineProducts(lineProducts).build();
		return irepository.save(action).getActionId();
	}
	
	
	@Transactional
	public String  editAction(ActionRequest actionRequest) {
		var actionFind =irepository.findByActionId(actionRequest.actionId()).orElseThrow(()->  new RuntimeException("Sku " + actionRequest.actionId() + "Not Found"));
		var lineProducts = actionRequest.lineProducts().stream().map(line -> {
				return new LineProduct(new InventoryId(actionRequest.actionId(), line.skuCode()), line.quantity(), null,null);
		}).collect(Collectors.toSet());
		actionFind.getLineProducts().clear();

		actionFind.setLineProducts(lineProducts);
		return irepository.save(actionFind).getActionId();
	}
	
	
	public String processeAction(String actionId) {
		ActionInventory action = irepository.findById(actionId).orElseThrow(() -> new RuntimeException("action"));
		action.setIsProcessed(true);
		return irepository.save(action).getActionId();
	}

	public List<InventoryWithActionsResponse> findAllWithAction() {

		var lineProducts = irepository.findAll().stream()
				.flatMap(
						action -> action.getLineProducts().stream()
								.map(t -> new InventoryWithActionResponse(t.getId().skuCode(), t.getQuantity(),
										new ActionForItemResponse(action.getActionId(), action.getActionDate(),
												t.getQuantity(), action.getActionType(), action.getDescription()))))
				.toList();

		Map<String, List<InventoryWithActionResponse>> mapInventory = lineProducts.stream()
				.collect(Collectors.groupingBy(t -> t.skuCode()));
		List<InventoryWithActionsResponse> inventory = mapInventory.keySet().stream()
				.map(t ->
				{
				var openingStock=findInventoryById(t).getOpeningStock();

				return new InventoryWithActionsResponse(t.toString(),openingStock,null,null ,mapInventory.get(t).stream()
						.mapToInt(q -> q.action().type().equals(ActionType.REMOVE_STOCK) ? q.stockQuantity() * -1
								: q.stockQuantity()+openingStock)
						.sum(), mapInventory.get(t).stream().map(x -> x.action()).toList());
				}
						
						)
				.toList();

		return inventory;
	}


	public List<InventoryWithActionsSummuryResponse> findAllWithActionSummury() {

		var lineProducts = irepository.findAll().stream()
				.flatMap(
						action -> action.getLineProducts().stream()
								.map(t -> new InventoryWithActionResponse(t.getId().skuCode(), t.getQuantity(),
										new ActionForItemResponse(action.getActionId(), action.getActionDate(),
												t.getQuantity(), action.getActionType(), action.getDescription()))))
				.toList();

		Map<String, List<InventoryWithActionResponse>> mapInventory = lineProducts.stream()
				.collect(Collectors.groupingBy(t -> t.skuCode()));
		List<InventoryWithActionsSummuryResponse> inventory = mapInventory.keySet().stream()
				.map(t ->
				{
				var openingStock=findInventoryById(t.toString()).getOpeningStock();
				var incoming= 
						mapInventory.get(t).stream().filter(q->q.action().type().equals(ActionType.ADD_STOCK))
						.mapToInt(q -> q.stockQuantity()
						 )
						
				.sum();
				var outoing=mapInventory.get(t).stream().filter(q->q.action().type().equals(ActionType.REMOVE_STOCK))
						.mapToInt(q -> q.stockQuantity()
								 ).sum();
				return new InventoryWithActionsSummuryResponse(t.toString(),openingStock ,
						incoming,outoing,openingStock+incoming-outoing);
				}
						
						)
				.toList();

		return inventory;
	}
	public List<InventoryWithActionsResponse> findAllWithActions() {

		var lineProducts = irepository.findAll().stream()
				.flatMap(
						action -> action.getLineProducts().stream()
								.map(t -> new InventoryWithActionResponse(t.getId().skuCode(), t.getQuantity(),
										new ActionForItemResponse(action.getActionId(), action.getActionDate(),
												t.getQuantity(), action.getActionType(), action.getDescription()))))
				.toList();

		Map<String, List<InventoryWithActionResponse>> mapInventory = lineProducts.stream()
				.collect(Collectors.groupingBy(t -> t.skuCode()));
		List<InventoryWithActionsResponse> inventory = mapInventory.keySet().stream()
				.map(t ->
				{
				var openingStock=findInventoryById(t.toString()).getOpeningStock();
				var incoming= 
						mapInventory.get(t).stream().filter(q->q.action().type().equals(ActionType.ADD_STOCK))
						.mapToInt(q -> q.stockQuantity()
						 )
						
				.sum();
				var outoing=mapInventory.get(t).stream().filter(q->q.action().type().equals(ActionType.REMOVE_STOCK))
						.mapToInt(q -> q.stockQuantity()
								 ).sum();
				var actions=	
						mapInventory.get(t).stream().map(inv->inv.action()).toList();		
				
				
				return new InventoryWithActionsResponse(t.toString(),openingStock ,
						incoming,outoing,openingStock+incoming-outoing,actions);
				}).toList();
		return inventory;
	}
	
	public InventoryWithActionsSummuryResponse findAllWithActionSummuryBySkuCode(String skuCode) {

		var lineProducts = irepository.findByLineProductsIdSkuCode(skuCode).stream()
				.flatMap(
						action -> action.getLineProducts().stream()
								.map(t -> new InventoryWithActionResponse(t.getId().skuCode(), t.getQuantity(),
										new ActionForItemResponse(action.getActionId(), action.getActionDate(),
												t.getQuantity(), action.getActionType(), action.getDescription()))))
				.toList();
		var openingStock=findInventoryById(skuCode).getOpeningStock();
		Map<String, List<InventoryWithActionResponse>> mapInventory = lineProducts.stream()
				.collect(Collectors.groupingBy(t -> t.skuCode()));
		InventoryWithActionsSummuryResponse inventory = mapInventory.keySet().stream().filter(s->s.equals(skuCode)
				
				).findFirst()
				.map(t ->
				{
					
				var incoming= 
						mapInventory.get(t).stream().filter(q->q.action().type().equals(ActionType.ADD_STOCK))
						.mapToInt(q -> q.stockQuantity()
						 )
						
				.sum();
				var outoing=mapInventory.get(t).stream().filter(q->q.action().type().equals(ActionType.REMOVE_STOCK))
						.mapToInt(q -> q.stockQuantity()
								 ).sum();
				return new InventoryWithActionsSummuryResponse(t.toString(),openingStock,
						incoming,outoing,openingStock+incoming-outoing);
				}
						
						)
				.orElse(new InventoryWithActionsSummuryResponse(skuCode,openingStock,
						0,0	,openingStock));

		return inventory;
	}
	public InventoryWithActionsResponse findAllWithActionBySkuCode(String skuCode) {

		var lineProducts = irepository.findByLineProductsIdSkuCode(skuCode).stream()
				.flatMap(
						action -> action.getLineProducts().stream()
								.map(t -> new InventoryWithActionResponse(t.getId().skuCode(), t.getQuantity(),
										new ActionForItemResponse(action.getActionId(), action.getActionDate(),
												t.getQuantity(), action.getActionType(), action.getDescription()))))
				.toList();
		var openingStock=findInventoryById(skuCode).getOpeningStock();
		Map<String, List<InventoryWithActionResponse>> mapInventory = lineProducts.stream()
				.collect(Collectors.groupingBy(t -> t.skuCode()));
		InventoryWithActionsResponse inventory = mapInventory.keySet().stream().filter(s->s.equals(skuCode)
				
				).findFirst()
				.map(t ->
				{
					
				var incoming= 
						mapInventory.get(t).stream().filter(q->q.action().type().equals(ActionType.ADD_STOCK))
						.mapToInt(q -> q.stockQuantity()
						 )
						
				.sum();
				var outoing=mapInventory.get(t).stream().filter(q->q.action().type().equals(ActionType.REMOVE_STOCK))
						.mapToInt(q -> q.stockQuantity()
								 ).sum();
				var actions=	
						mapInventory.get(t).stream().map(inv->inv.action()).toList();		
				
								
						
				return new InventoryWithActionsResponse(t.toString(),openingStock,
						incoming,outoing,openingStock+incoming-outoing,actions);
				}
						
						)
				.orElse(new InventoryWithActionsResponse(skuCode,openingStock,
						0,0	,openingStock,null));

		return inventory;
	}


	public ActionResponse findByActionId(String actionId) {
		var action = irepository.findByActionId(actionId)
				.orElseThrow(() -> new RuntimeException("action is not found"));

		return new ActionResponse(action.getActionId(), action.getActionDate(), action.getActionType(),
				action.getDescription(), action.getLineProducts().stream()
						.map(l -> new LineProductOfInventory(l.getId().skuCode(), l.getQuantity(),null)).toList());
	}

	public List<ActionResponse> findByIsNotProcessed() {
		List<ActionInventory> actions = irepository.findByIsProcessedAndIsPublished(false, false,
				Sort.by("actionDate").descending());
		return actions.stream()
				.map(action -> new ActionResponse(action.getActionId(), action.getActionDate(), action.getActionType(),
						action.getDescription(),
						action.getLineProducts().stream()
								.map(l -> new LineProductOfInventory(l.getId().skuCode(), l.getQuantity(),null)).toList()))
				.toList();
	}

	public List<ActionResponse> findAllActions() {
		List<ActionInventory> actions = irepository.findAll();
		return actions.stream()
				.map(action -> new ActionResponse(action.getActionId(), action.getActionDate(), action.getActionType(),
						action.getDescription(),
						action.getLineProducts().stream()
								.map(l -> new LineProductOfInventory(l.getId().skuCode(), l.getQuantity(),null)).toList()))
				.toList();
	}

	public List<InventoryOutActionsResponse> findAllInventoryOutActions(Integer pageNumber) {

		var lineProducts = irepository.findAll().stream()
				.flatMap(
						action -> action.getLineProducts().stream()
								.map(t -> new InventoryWithActionResponse(t.getId().skuCode(), t.getQuantity(),
										new ActionForItemResponse(action.getActionId(), action.getActionDate(),
												t.getQuantity(), action.getActionType(), action.getDescription()))))
				.toList();

		Map<String, List<InventoryWithActionResponse>> mapInventory = lineProducts.stream()
				.collect(Collectors.groupingBy(t -> t.skuCode()));

		List<InventoryOutActionsResponse> inventory = mapInventory
				.keySet().stream().map(
						t -> new InventoryOutActionsResponse(t.toString(),
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
						t.getActionId(), t.getActionDate(), t.getLineProducts().stream()
								.filter(l -> l.getId().skuCode().equals(skuCode)).findFirst().get().getQuantity(),
						t.getActionType(), t.getDescription()))
				.toList();

		return act;
	}

	public InventoryOutActionsResponse findInventoryBySkuCode(String skuCode) {
		int quantity = irepository.findByLineProductsIdSkuCode(skuCode).stream()
				.flatMap(t -> t.getLineProducts().stream()).filter(l -> l.getId().skuCode().equals(skuCode))
				.mapToInt(q -> q.getQuantity()).sum();
		return new InventoryOutActionsResponse(skuCode, quantity);
	}

	public List<InventoryOutActionsResponse> findInventoryBySkuCodes(List<String> skuCodes) {
		var invs = skuCodes.stream().map(skuCode -> findInventoryBySkuCode(skuCode)).toList();

		return invs;
	}

//	public void eventPublishing() {
//		List<ActionInventory> actions = irepository.findByIsProcessedAndIsPublished(true, false,
//				Sort.by("actionDate").descending());
//		actions.stream().forEachOrdered(t -> {
//			if (t.getActionType().equals(ActionType.REMOVE_STOCK)) {
//				publish.publish(new CreatedOrderEvent(null, t.getActionId(), t.getLineProducts().stream()
//						.map(l -> new LineProductOfInventory(l.getId().skuCode(), l.getQuantity())).toList()));
//				t.setIsPublished(true);
//				irepository.save(t);
//			}
//		});
//	}

	private boolean checkQuntity(List<LineProductOfInventory> lineProducts) {

		return lineProducts.stream()
				.anyMatch(t -> findInventoryBySkuCode(t.skuCode()).stockQuantity() - t.quantity() < 0);
	}

	public OpeningStock addOpeningStock(OpeningStock request) {
		
		  inventoryRepository
				.save(Inventory.builder().skuCode(request.skuCode()).openingStock(request.openingStock()).build());
		  return request;
		  
		  
	}
}
