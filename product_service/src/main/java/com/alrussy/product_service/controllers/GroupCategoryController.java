package com.alrussy.product_service.controllers;

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryRequest;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryResponse;
import com.alrussy.product_service.model.dto.group_category_dto.GroupFilter;
import com.alrussy.product_service.service.BaseGroupCategoryService;
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
@RequestMapping("/products/group-categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class GroupCategoryController {

    @Qualifier("groupCategoryServiceImpl")
    private final BaseGroupCategoryService service;

    @GetMapping
    public ResponseEntity<PagedResult<GroupCategoryResponse>> findAll(
            @RequestParam(defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok(service.findAll(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupCategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<GroupCategoryResponse>> findById(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody GroupCategoryRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupCategoryResponse> update(
            @PathVariable Long id, @RequestBody GroupCategoryRequest request) throws Exception {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("delete By " + id + " is successfuly");
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GroupCategoryResponse>> filter(
            @RequestParam String name, @RequestParam(required = false) Long categoryId) {
        var filter = new GroupFilter(name, categoryId);
        System.out.println(filter);
        return ResponseEntity.ok(service.filter(filter));
    }
}
