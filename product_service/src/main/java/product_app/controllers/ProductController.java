package product_app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import product_app.model.dto.PagedResult;
import product_app.model.dto.product_dto.ProductResponse;
import product_app.service.BaseProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    @Qualifier("productServiceForAdmin")
    final BaseProductService service;

    @GetMapping
    public ResponseEntity<PagedResult<ProductResponse>> findAll(@RequestParam(defaultValue = "1") Integer pageNumber) {
        return ResponseEntity.ok(service.findAll(pageNumber));
    }
}
