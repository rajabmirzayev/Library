package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateMemberRequest;
import az.library.library.dto.request.UpdateMemberRequest;
import az.library.library.dto.response.MemberDetailedResponse;
import az.library.library.dto.response.MemberSummaryResponse;
import az.library.library.dto.response.PageResponse;
import az.library.library.service.MemberService;
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
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Üzvlər", description = "Üzv əməliyyatları")
public class MemberController {
    private final MemberService service;

    @Operation(summary = "Yeni üzv yaratmaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Üzv yaradıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resurs tapılmadı", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ApiResponse<MemberDetailedResponse>> create(@Valid @RequestBody CreateMemberRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @Operation(summary = "Üzvü ID ilə tapmaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Üzv tapıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Üzv tapılmadı", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDetailedResponse>> findById(
            @Parameter(description = "Üzvün ID-si") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @Operation(summary = "Bütün üzvləri səhifələmə ilə siyahıya almaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Siyahı qaytarıldı")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<MemberSummaryResponse>>> findAll(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @Operation(summary = "Üzvü yeniləmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Üzv yeniləndi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Üzv tapılmadı", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDetailedResponse>> update(
            @Parameter(description = "Üzvün ID-si") @PathVariable Long id,
            @Valid @RequestBody UpdateMemberRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @Operation(summary = "Üzvü silmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Üzv silindi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Üzv tapılmadı", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "Üzvün ID-si") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
