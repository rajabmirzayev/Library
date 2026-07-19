package az.library.library.dto.response;

import az.library.library.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Borcalmanın ətraflı cavabı")
public class LoanDetailedResponse {

    @Schema(description = "Borcalmanın ID-si", example = "1")
    private Long id;

    @Schema(description = "Borcalma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime loanDate;

    @Schema(description = "Qayıtma tarixi", example = "2026-08-15")
    private LocalDate dueDate;

    @Schema(description = "Gerçək qayıtma tarixi", example = "2026-08-10T14:20:00")
    private LocalDateTime returnDate;

    @Schema(description = "Borcalma statusu", example = "ACTIVE")
    private LoanStatus status;

    @Schema(description = "Kitab nüsxəsinin ID-si", example = "1")
    private Long bookCopyId;

    @Schema(description = "Kitab nüsxəsinin barkodu", example = "BK-2026-001")
    private String bookBarcode;

    @Schema(description = "Kitabın adı", example = "Üçippyşq")
    private String bookTitle;

    @Schema(description = "Üzvün ID-si", example = "1")
    private Long memberId;

    @Schema(description = "Üzvün adı soyadı", example = "Əli Həsənov")
    private String memberName;

    @Schema(description = "Yaradılma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Yenilənmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime updatedAt;

}
