package com.alrussy.product_service.controllers;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.brand_dto.BrandFilter;
import com.alrussy.product_service.model.dto.brand_dto.BrandRequest;
import com.alrussy.product_service.model.dto.brand_dto.BrandResponse;
import com.alrussy.product_service.service.BaseBrandService;

@RestController
@RequestMapping("/products/brands")
@CrossOrigin("*")
public class BrandController {

    @Autowired
    @Qualifier("brandServiceImpl")
    private BaseBrandService brandService;

    @GetMapping
    ResponseEntity<PagedResult<BrandResponse>> findAll(@RequestParam(defaultValue = "1") Integer pageNumber) {
        return ResponseEntity.ok(brandService.findAll(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.findByIdWithProductCount(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Collection<BrandResponse>> findByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.findByCategoryWithProductCount(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<BrandResponse>> filter(
            @RequestParam String name, @RequestParam(required = false) Long categoryId) {
        var filter = new BrandFilter(name, categoryId);
        System.out.println(filter);
        return ResponseEntity.ok(brandService.filter(filter));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<BrandResponse>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(brandService.findByName(name));
    }

    @GetMapping("/name/{name}/category/{categoryId}")
    public ResponseEntity<List<BrandResponse>> findByNameAndCategoryId(
            @PathVariable String name, @PathVariable Long categoryId) {
        return ResponseEntity.ok(brandService.findByNameAndCategoryId(name, categoryId));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Valid BrandRequest brand) throws Exception {
        return ResponseEntity.ok(brandService.save(brand));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> update(@PathVariable Long id, @RequestBody BrandRequest brand)
            throws Exception {
        return ResponseEntity.ok(brandService.update(id, brand));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.ok("delete By " + id + " is successfuly");
    }
}
