package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.response.LibraryOverviewResponse;
import az.library.library.dto.response.MostBorrowedBookResponse;
import az.library.library.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Tag(name = "Hesabatlar", description = "Admin analitik hesabatları (native SQL)")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Kitabxana statistikası",
            description = "Kitab, nüsxə, üzv, kredit, rezervasiya və cərimə göstəricilərinin birləşmiş ümumi görünüşü (tək native sorğu)")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Statistika qaytarıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Qadağan olunmuş giriş", content = @Content)
    })
    @GetMapping("/overview")
    public ResponseEntity<ApiResponse<LibraryOverviewResponse>> overview() {
        return ResponseEntity.ok(ApiResponse.success(reportService.getOverview()));
    }

    @Operation(summary = "Ən çox icarəyə götürülən kitablar",
            description = "Kredit sayına görə azalan sırada limit-lənmiş siyahı; opsional tarix aralığı ilə filtr")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Siyahı qaytarıldı"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Qadağan olunmuş giriş", content = @Content)
    })
    @GetMapping("/most-borrowed-books")
    public ResponseEntity<ApiResponse<List<MostBorrowedBookResponse>>> mostBorrowedBooks(
            @Parameter(description = "Nəticə sayı (limit)", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Başlanğıc tarix (daxil)", example = "2026-01-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @Parameter(description = "Son tarix (daxil deyil)", example = "2026-12-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getMostBorrowedBooks(limit, from, to)));
    }
}
