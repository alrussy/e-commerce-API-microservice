package com.alrussy.product_service.controllers;

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.category_dto.CategoryFilter;
import com.alrussy.product_service.model.dto.category_dto.CategoryRequest;
import com.alrussy.product_service.model.dto.category_dto.CategoryResponse;
import com.alrussy.product_service.service.BaseCategoryService;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/products/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    @Qualifier("categoryServiceImpl")
    private final BaseCategoryService categoryService;

    @GetMapping
    public ResponseEntity<PagedResult<CategoryResponse>> findAll(@RequestParam(defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok(categoryService.findAll(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<CategoryResponse>> findByGroupId(@PathVariable Long groupId) {
        return ResponseEntity.ok(categoryService.findByGroupId(groupId));
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<CategoryResponse>> findByBrandId(@PathVariable Long brandId) {
        return ResponseEntity.ok(categoryService.findByBrandId(brandId));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Collection<CategoryResponse>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.findByName(name));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CategoryResponse>> filter(
            @RequestParam String name,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long groupId) {
        var filter = new CategoryFilter(name, groupId, brandId, null);
        System.out.println(filter);
        return ResponseEntity.ok(categoryService.filter(filter));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CategoryRequest category) {
        return ResponseEntity.ok(categoryService.save(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest category)
            throws Exception {
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    @DeleteMapping("/department/delete-department")
    public ResponseEntity<Integer> deleteDepartment(@RequestParam Long categoryId, @RequestParam Long departmentId) {
        return ResponseEntity.ok(categoryService.deleteDepartment(categoryId, departmentId));
    }

    @DeleteMapping("/name-details/delete-name_details")
    public ResponseEntity<Integer> deleteNameDetails(@RequestParam Long categoryId, @RequestParam String nameDetails) {
        return ResponseEntity.ok(categoryService.deleteNameDetails(categoryId, nameDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        System.out.println(id);
        categoryService.delete(id);
        return ResponseEntity.ok("delete By " + id + " is successfuly");
    }
}
