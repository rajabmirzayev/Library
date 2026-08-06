package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.BookSearchCriteria;
import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.request.UpdateBookRequest;
import az.library.library.dto.response.BookDetailedResponse;
import az.library.library.dto.response.BookSummaryResponse;
import az.library.library.dto.response.PageResponse;
import az.library.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Kitablar", description = "Kitab əməliyyatları")
public class BookController {
    private final BookService service;

    @Operation(summary = "Yeni kitab yaratmaq", description = "Verilən məlumatlara əsasən yeni kitab əlavə edir")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Kitab uğurla yaradıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resurs tapılmadı", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ApiResponse<BookDetailedResponse>> create(@Valid @RequestBody CreateBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(request)));
    }

    @Operation(summary = "Kitabı ID ilə tapmaq", description = "Verilən ID-yə uyğun kitabı qaytarır")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Kitab tapıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Kitab tapılmadı", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDetailedResponse>> findById(
            @Parameter(description = "Kitabın ID-si") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @Operation(summary = "Bütün kitabları səhifələmə ilə siyahıya almaq")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Siyahı qaytarıldı")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BookSummaryResponse>>> findAll(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @Operation(summary = "Kitabları dinamik meyarlarla axtarmaq",
            description = "Title, authorId, categoryId, publisherId, isbn, minPrice/maxPrice, startYear/endYear — bütün parametrlər opsionaldır, yalnız doldurulanlar filtrə daxil edilir")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Axtarış nəticəsi qaytarıldı")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<BookSummaryResponse>>> search(
            @ParameterObject BookSearchCriteria criteria,
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.search(criteria, pageable))));
    }

    @Operation(summary = "Kitabı yeniləmək", description = "Verilən ID-yə uyğun kitabın məlumatlarını yeniləyir")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Kitab yeniləndi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validasiya xətası", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Kitab tapılmadı", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDetailedResponse>> update(
            @Parameter(description = "Kitabın ID-si") @PathVariable Long id,
            @Valid @RequestBody UpdateBookRequest request) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, request)));
    }

    @Operation(summary = "Kitabı silmək")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Kitab silindi"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Kitab tapılmadı", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "Kitabın ID-si") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
