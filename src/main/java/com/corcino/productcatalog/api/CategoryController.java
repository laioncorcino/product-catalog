package com.corcino.productcatalog.api;

import com.corcino.productcatalog.api.docs.CategoryDocs;
import com.corcino.productcatalog.json.CategoryRequest;
import com.corcino.productcatalog.json.CategoryResponse;
import com.corcino.productcatalog.model.Category;
import com.corcino.productcatalog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController implements CategoryDocs {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long categoryId) {
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid CategoryRequest categoryRequest, UriComponentsBuilder uriBuilder) throws Exception {
        Category category = categoryService.createCategory(categoryRequest);
        URI uri = uriBuilder.path("/v1/categories/{categoryId}").buildAndExpand(category.getCategoryId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value ="/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse> update(@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable Long categoryId) throws Exception {
        CategoryResponse category = categoryService.updateCategory(categoryRequest, categoryId);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}


