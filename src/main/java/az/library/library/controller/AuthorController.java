package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateAuthorRequest;
import az.library.library.dto.request.UpdateAuthorRequest;
import az.library.library.dto.response.AuthorDetailedResponse;
import az.library.library.dto.response.AuthorSummaryResponse;
import az.library.library.dto.response.PageResponse;
import az.library.library.service.AuthorService;
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
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Müəlliflər", description = "Müəllif əməliyyatları")
public class AuthorController {
    private final AuthorService service;

    @Operation(summary = "Yeni müəllif yaratmaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Müəllif yaradıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resurs tapılmadı", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDetailedResponse>> create(@Valid @RequestBody CreateAuthorRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @Operation(summary = "Müəllifi ID ilə tapmaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Müəllif tapıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Müəllif tapılmadı", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorDetailedResponse>> findById(
            @Parameter(description = "Müəllifin ID-si") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @Operation(summary = "Bütün müəllifləri səhifələmə ilə siyahıya almaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Siyahı qaytarıldı")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AuthorSummaryResponse>>> findAll(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @Operation(summary = "Müəllifi yeniləmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Müəllif yeniləndi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Müəllif tapılmadı", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorDetailedResponse>> update(
            @Parameter(description = "Müəllifin ID-si") @PathVariable Long id,
            @Valid @RequestBody UpdateAuthorRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @Operation(summary = "Müəllifi silmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Müəllif silindi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Müəllif tapılmadı", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "Müəllifin ID-si") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
