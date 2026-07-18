package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateCategoryRequest;
import az.library.library.dto.request.UpdateCategoryRequest;
import az.library.library.dto.response.CategoryDetailedResponse;
import az.library.library.dto.response.CategorySummaryResponse;
import az.library.library.dto.response.PageResponse;
import az.library.library.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDetailedResponse>> create(@Valid @RequestBody CreateCategoryRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailedResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategorySummaryResponse>>> findAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDetailedResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateCategoryRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
