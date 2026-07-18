package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateFineRequest;
import az.library.library.dto.request.UpdateFineRequest;
import az.library.library.dto.response.FineDetailedResponse;
import az.library.library.dto.response.FineSummaryResponse;
import az.library.library.dto.response.PageResponse;
import az.library.library.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fines")
@RequiredArgsConstructor
public class FineController {
    private final FineService service;

    @PostMapping
    public ResponseEntity<ApiResponse<FineDetailedResponse>> create(@Valid @RequestBody CreateFineRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FineDetailedResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<FineSummaryResponse>>> findAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FineDetailedResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateFineRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
