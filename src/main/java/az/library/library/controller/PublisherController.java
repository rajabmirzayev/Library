package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreatePublisherRequest;
import az.library.library.dto.request.UpdatePublisherRequest;
import az.library.library.dto.response.PageResponse;
import az.library.library.dto.response.PublisherDetailedResponse;
import az.library.library.dto.response.PublisherSummaryResponse;
import az.library.library.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PublisherDetailedResponse>> create(@Valid @RequestBody CreatePublisherRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PublisherDetailedResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PublisherSummaryResponse>>> findAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PublisherDetailedResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdatePublisherRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
