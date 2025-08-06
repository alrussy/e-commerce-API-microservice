package product_app.controllers;

import java.util.Collection;
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
import product_app.model.dto.PagedResult;
import product_app.model.dto.category_dto.CategoryRequest;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.service.BaseCategoryService;

@RestController
@RequestMapping("/api/products/categories")
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

    @GetMapping("/filter")
    public ResponseEntity<Collection<CategoryResponse>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.findByName(name));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok("delete By " + id + " is successfuly");
    }
}
