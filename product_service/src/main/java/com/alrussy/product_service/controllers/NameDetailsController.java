package com.alrussy.product_service.controllers;

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsRequest;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsResponse;
import com.alrussy.product_service.service.BaseNameDetailsService;
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
@RequestMapping("/products/details")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NameDetailsController {

    @Qualifier("NameDetailsServiceImpl")
    private final BaseNameDetailsService service;

    @GetMapping
    public ResponseEntity<PagedResult<NameDetailsResponse>> findAll(@RequestParam(defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok(service.findAll(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NameDetailsResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/by-names")
    public ResponseEntity<List<NameDetailsResponse>> findById(@RequestParam List<String> names) {
        System.out.println(names);
        return ResponseEntity.ok(service.findByIds(names));
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody NameDetailsRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @PutMapping("/add-value")
    public ResponseEntity<NameDetailsResponse> addValue(@RequestBody NameDetailsRequest request) {
        return ResponseEntity.ok(service.addValue(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok("delete By " + id + " is successfuly");
    }
}
