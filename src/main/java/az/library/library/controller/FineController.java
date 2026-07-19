package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateFineRequest;
import az.library.library.dto.request.UpdateFineRequest;
import az.library.library.dto.response.FineDetailedResponse;
import az.library.library.dto.response.FineSummaryResponse;
import az.library.library.dto.response.PageResponse;
import az.library.library.service.FineService;
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
@RequestMapping("/api/v1/fines")
@RequiredArgsConstructor
@Tag(name = "Cərimələr", description = "Cərimə əməliyyatları")
public class FineController {
    private final FineService service;

    @Operation(summary = "Yeni cərimə yaratmaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Cərimə yaradıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resurs tapılmadı", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ApiResponse<FineDetailedResponse>> create(@Valid @RequestBody CreateFineRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @Operation(summary = "Cəriməni ID ilə tapmaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cərimə tapıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cərimə tapılmadı", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FineDetailedResponse>> findById(
            @Parameter(description = "Cərimənin ID-si") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @Operation(summary = "Bütün cərimələri səhifələmə ilə siyahıya almaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Siyahı qaytarıldı")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<FineSummaryResponse>>> findAll(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @Operation(summary = "Cəriməni yeniləmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cərimə yeniləndi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cərimə tapılmadı", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FineDetailedResponse>> update(
            @Parameter(description = "Cərimənin ID-si") @PathVariable Long id,
            @Valid @RequestBody UpdateFineRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @Operation(summary = "Cəriməni silmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Cərimə silindi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cərimə tapılmadı", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "Cərimənin ID-si") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
