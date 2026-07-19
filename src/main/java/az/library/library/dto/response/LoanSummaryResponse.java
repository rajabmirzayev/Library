package az.library.library.dto.response;

import az.library.library.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Borcalmanın qısa siyahı cavabı")
public class LoanSummaryResponse {

    @Schema(description = "Borcalmanın ID-si", example = "1")
    private Long id;

    @Schema(description = "Qayıtma tarixi", example = "2026-08-15")
    private LocalDate dueDate;

    @Schema(description = "Status", example = "ACTIVE")
    private LoanStatus status;

    @Schema(description = "Kitabın adı", example = "Üçippyşq")
    private String bookTitle;

    @Schema(description = "Üzvün adı soyadı", example = "Əli Həsənov")
    private String memberName;

}
