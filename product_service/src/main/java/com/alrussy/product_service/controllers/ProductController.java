package com.alrussy.product_service.controllers;

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.product_dto.ProductFilter;
import com.alrussy.product_service.model.dto.product_dto.ProductRequest;
import com.alrussy.product_service.model.dto.product_dto.ProductResponse;
import com.alrussy.product_service.service.BaseProductService;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    @Qualifier("productServiceImpl")
    private final BaseProductService service;

    @GetMapping
    public ResponseEntity<PagedResult<ProductResponse>> findAll(@RequestParam(defaultValue = "0") Integer pageNumber) {

        return ResponseEntity.ok(service.findAll(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponse>> filter(
            @RequestParam String name,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long departmentId) {
        var filter = new ProductFilter(name, groupId, categoryId, brandId, departmentId);
        System.out.println(filter.departmentId());
        return ResponseEntity.ok(service.filter(filter));
    }

    @GetMapping("/pages")
    public ResponseEntity<PagedResult<ProductResponse>> filter(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "name") String[] sortedby,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long departmentId) {
        var filter = new ProductFilter(name, groupId, categoryId, brandId, departmentId);
        return ResponseEntity.ok(service.filter(pageNumber, filter, direction, sortedby));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductResponse>> findById(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/sku-code/{skucode}")
    public ResponseEntity<List<ProductResponse>> findByskuCode(@PathVariable String skucode) {
        return ResponseEntity.ok(service.findBySkuCode(skucode));
    }

    @GetMapping("/category/{categoryId}/name/{name}")
    public ResponseEntity<List<ProductResponse>> findByIdCategoryAndName(
            @PathVariable Long categoryId, @PathVariable String name) {
        return ResponseEntity.ok(service.findByCategoryIdAnName(categoryId, name));
    }

    @GetMapping("/brand/{brandId}/name/{name}")
    public ResponseEntity<List<ProductResponse>> findById(@PathVariable Long brandId, @PathVariable String name) {
        return ResponseEntity.ok(service.findByBrandIdAndName(brandId, name));
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<Collection<ProductResponse>> findByBrandId(@PathVariable(value = "id") Long brandsId) {
        return ResponseEntity.ok(service.findByBrandId(brandsId));
    }

    @GetMapping("/by-department-ids")
    public ResponseEntity<Map<Object, List<ProductResponse>>> findByDepartmentId(
            @RequestParam List<String> departmentIds) {
        return ResponseEntity.ok(service.findByDepartmentIds(departmentIds));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Valid ProductRequest product) {
        return ResponseEntity.ok(service.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest product)
            throws Exception {
        return ResponseEntity.ok(service.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
