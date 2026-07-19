package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreatePublisherRequest;
import az.library.library.dto.request.UpdatePublisherRequest;
import az.library.library.dto.response.PageResponse;
import az.library.library.dto.response.PublisherDetailedResponse;
import az.library.library.dto.response.PublisherSummaryResponse;
import az.library.library.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
@Tag(name = "Nəşriyyatlar", description = "Nəşriyyat əməliyyatları")
public class PublisherController {
    private final PublisherService service;

    @Operation(summary = "Yeni nəşriyyat yaratmaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Nəşriyyat yaradıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resurs tapılmadı", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ApiResponse<PublisherDetailedResponse>> create(@Valid @RequestBody CreatePublisherRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @Operation(summary = "Nəşriyyatı ID ilə tapmaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Nəşriyyat tapıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Nəşriyyat tapılmadı", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PublisherDetailedResponse>> findById(
            @Parameter(description = "Nəşriyyatın ID-si") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @Operation(summary = "Bütün nəşriyyatları səhifələmə ilə siyahıya almaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Siyahı qaytarıldı")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PublisherSummaryResponse>>> findAll(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @Operation(summary = "Nəşriyyatı yeniləmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Nəşriyyat yeniləndi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Nəşriyyat tapılmadı", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PublisherDetailedResponse>> update(
            @Parameter(description = "Nəşriyyatın ID-si") @PathVariable Long id,
            @Valid @RequestBody UpdatePublisherRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @Operation(summary = "Nəşriyyatı silmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Nəşriyyat silindi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Nəşriyyat tapılmadı", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "Nəşriyyatın ID-si") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
