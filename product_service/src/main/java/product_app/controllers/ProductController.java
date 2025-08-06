package product_app.controllers;

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
import product_app.model.dto.PagedResult;
import product_app.model.dto.product_dto.ProductRequest;
import product_app.model.dto.product_dto.ProductResponse;
import product_app.service.BaseProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    @Qualifier("productServiceImpl")
    final BaseProductService service;

    @GetMapping
    public ResponseEntity<PagedResult<ProductResponse>> findAll(@RequestParam(defaultValue = "1") Integer pageNumber) {
        return ResponseEntity.ok(service.findAll(pageNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
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
