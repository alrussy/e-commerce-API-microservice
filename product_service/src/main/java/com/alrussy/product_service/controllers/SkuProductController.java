package com.alrussy.product_service.controllers;

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductFilter;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductRequest;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductResponse;
import com.alrussy.product_service.service.BaseSkuProductService;
import com.alrussy.product_service.service.impls.sku_product_Impl.SkuProductServiceImpl;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/products/sku")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SkuProductController {

    @Qualifier("skuProductServiceImpl")
    @Autowired
    private BaseSkuProductService skuProductService;

    @GetMapping
    public ResponseEntity<PagedResult<SkuProductResponse>> findAll(@RequestParam(defaultValue = "1") int pageNumber) {

        return ResponseEntity.ok(skuProductService.findAll(pageNumber));
    }

    @GetMapping("/primary/page")
    public ResponseEntity<PagedResult<SkuProductResponse>> findByIsPrimaryPageable(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(skuProductService.findByIsPrimary(pageNumber, pageSize));
    }

    @GetMapping("/primary/page/product/skuCodes")
    public ResponseEntity<PagedResult<SkuProductResponse>> findByIsPrimaryPageable(
            @RequestParam List<String> skuCodes,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(skuProductService.findByProductSkuCodesInAndIsPrimary(skuCodes, pageNumber, pageSize));
    }

    @GetMapping("/pages")
    public ResponseEntity<PagedResult<SkuProductResponse>> filter(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "skuCode") String[] sortedby,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "") String skuCode,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Double priceGraeteThan,
            @RequestParam(required = false) Double priceGraeteThanOrEquel,
            @RequestParam(required = false) Double priceLessThanOrEquel,
            @RequestParam(required = false) Double priceLessThan,
            @RequestParam(required = false) Double priceEquel) {

        var filter = new SkuProductFilter(
                skuCode,
                productId,
                groupId,
                categoryId,
                brandId,
                departmentId,
                priceGraeteThan,
                priceLessThan,
                priceLessThanOrEquel,
                priceGraeteThanOrEquel,
                priceEquel);

        return ResponseEntity.ok(skuProductService.filter(pageNumber, filter, direction, sortedby));
    }

    @GetMapping("/details-values/{productId}")
    public ResponseEntity<List<SkuProductResponse>> findByDetailsValues(
            @PathVariable Long productId, @RequestParam("values") List<String> values) {
        return ResponseEntity.ok(skuProductService.findByValueDetails(productId, values));
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<SkuProductResponse> findById(@PathVariable String skuCode) {

        return ResponseEntity.ok(skuProductService.findById(skuCode));
    }

    @GetMapping("/details/{productId}")
    public ResponseEntity<?> getAllDetailsByProductId(@PathVariable Long productId) {
        var skuProductServiceImpl = (SkuProductServiceImpl) skuProductService;
        return ResponseEntity.ok(skuProductServiceImpl.getAllDetailsByProductId(productId));
    }

    @GetMapping("/skuCodes")
    public ResponseEntity<List<SkuProductResponse>> findByIds(@RequestParam List<String> skuCodes) {
        return ResponseEntity.ok(skuProductService.findByIds(skuCodes));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> findByProductId(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(skuProductService.findByProduct(productId));
    }

    @GetMapping("/category-id/{id}")
    public ResponseEntity<?> findByCategoryId(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(skuProductService.findByCategoryId(categoryId));
    }

    @GetMapping("/department-ids")
    public ResponseEntity<Map<Object, List<SkuProductResponse>>> findByDepartmentIds(
            @RequestParam List<Long> departmentIds) {
        return ResponseEntity.ok(skuProductService.findByDepartmentIds(departmentIds));
    }

    @GetMapping("/brand-ids")
    public ResponseEntity<Map<Object, List<SkuProductResponse>>> findByBrandIds(@RequestParam List<Long> brandIds) {
        return ResponseEntity.ok(skuProductService.findByBrandIds(brandIds));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<SkuProductResponse>> findByDepartmentId(@PathVariable Long departmentId) {
        return ResponseEntity.ok(skuProductService.findByDepartmentId(departmentId));
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<SkuProductResponse>> findByBrandId(@PathVariable Long brandId) {
        return ResponseEntity.ok(skuProductService.findByBrandId(brandId));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody SkuProductRequest request) {
        skuProductService.save(request);
        return ResponseEntity.ok(1L);
    }

    @PutMapping("/{skuCode}")
    public ResponseEntity<SkuProductResponse> update(
            @PathVariable String skuCode, @RequestBody SkuProductRequest request) throws Exception {
        return ResponseEntity.ok(skuProductService.update(skuCode, request));
    }

    @PutMapping("/add-image")
    public ResponseEntity<SkuProductResponse> addImage(@RequestParam String skuCode, @RequestParam String url) {
        return ResponseEntity.ok(((SkuProductServiceImpl) skuProductService).addAndRemoveImage(skuCode, url, 1));
    }

    @DeleteMapping("/remove-image")
    public ResponseEntity<SkuProductResponse> removeImage(@RequestParam String id, @RequestParam String url) {
        return ResponseEntity.ok(((SkuProductServiceImpl) skuProductService).addAndRemoveImage(id, url, 0));
    }

    @DeleteMapping("/{skuCode}")
    public ResponseEntity<Void> delete(@PathVariable String skuCode) {
        skuProductService.delete(skuCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
