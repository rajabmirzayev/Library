package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateBookCopyRequest;
import az.library.library.dto.request.UpdateBookCopyRequest;
import az.library.library.dto.response.BookCopyDetailedResponse;
import az.library.library.dto.response.BookCopySummaryResponse;
import az.library.library.service.BookCopyService;
import jakarta.validation.Valid;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book-copies")
@RequiredArgsConstructor
public class BookCopyController {
    private final BookCopyService service;

    @PostMapping
    public ResponseEntity<ApiResponse<BookCopyDetailedResponse>> create(@Valid @RequestBody CreateBookCopyRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookCopyDetailedResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookCopySummaryResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(service.findAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookCopyDetailedResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateBookCopyRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
