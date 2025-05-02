package product_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import product_app.model.dto.PageResponse;
import product_app.model.dto.product.ProductResponse;
import product_app.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
	final ProductService service;
	
	@GetMapping
	public ResponseEntity<PageResponse<ProductResponse>> findAll(@RequestParam(defaultValue = "1") Integer pageNumber){
		return ResponseEntity.ok(service.findAll(pageNumber));
	}

}
