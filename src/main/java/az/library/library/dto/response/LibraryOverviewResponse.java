package az.library.library.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Kitabxananın ümumi statistikası")
public record LibraryOverviewResponse(
        @Schema(description = "Ümumi kitab sayı", example = "150")
        long totalBooks,

        @Schema(description = "Ümumi nüsxə sayı", example = "320")
        long totalCopies,

        @Schema(description = "Mövcud (AVAILABLE) nüsxə sayı", example = "180")
        long availableCopies,

        @Schema(description = "Ümumi üzv sayı", example = "95")
        long totalMembers,

        @Schema(description = "Aktiv kredit sayı (qaytarılmamış)", example = "42")
        long activeLoans,

        @Schema(description = "Vaxtı keçmiş kredit sayı", example = "7")
        long overdueLoans,

        @Schema(description = "Gözləyən rezervasiya sayı", example = "12")
        long pendingReservations,

        @Schema(description = "Gözləyən cərimə sayı", example = "9")
        long pendingFines,

        @Schema(description = "Yığılmış cərimə məbləği (AZN)", example = "250.50")
        BigDecimal collectedFineRevenue,

        @Schema(description = "Ümumi cərimə məbləği (AZN)", example = "410.00")
        BigDecimal totalFineAmount
) {
}
