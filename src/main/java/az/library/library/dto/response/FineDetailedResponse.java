package az.library.library.dto.response;

import az.library.library.enums.FineStatus;
import az.library.library.enums.FineType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Cərimənin ətraflı cavabı")
public class FineDetailedResponse {

    @Schema(description = "Cərimənin ID-si", example = "1")
    private Long id;

    @Schema(description = "Cərimə məbləği (AZN)", example = "25.50")
    private BigDecimal amount;

    @Schema(description = "Cərimə səbəbi", example = "Gecikmiş qayıtma")
    private String reason;

    @Schema(description = "Cərimə növü", example = "LATE_RETURN")
    private FineType type;

    @Schema(description = "Cərimə statusu", example = "PENDING")
    private FineStatus status;

    @Schema(description = "Tətbiq edilmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime issuedDate;

    @Schema(description = "Ödəniş tarixi", example = "2026-07-20T12:00:00")
    private LocalDateTime paidDate;

    @Schema(description = "Əlaqədar borcalma ID-si", example = "1")
    private Long loanId;

    @Schema(description = "Üzvün ID-si", example = "1")
    private Long memberId;

    @Schema(description = "Üzvün adı soyadı", example = "Əli Həsənov")
    private String memberName;

    @Schema(description = "Yaradılma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Yenilənmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime updatedAt;

}
