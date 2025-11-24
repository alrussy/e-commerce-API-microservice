package com.alrussy.product_service.controllers;

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.unit_dto.UnitRequest;
import com.alrussy.product_service.model.dto.unit_dto.UnitResponse;
import com.alrussy.product_service.service.BaseUnitService;
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
@RequestMapping("/products/units")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UnitController {

    @Qualifier("unitServiceImpl")
    private final BaseUnitService service;

    @GetMapping
    public ResponseEntity<PagedResult<UnitResponse>> findAll(@RequestParam(defaultValue = "1") int pageNumber) {
        return ResponseEntity.ok(service.findAll(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody UnitRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitResponse> update(@PathVariable Long id, @RequestBody UnitRequest request)
            throws Exception {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("delete By " + id + " is successfuly");
    }
}
