package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Yeni borcalma (kredit) yaratmaq üçün sorğu")
public class CreateLoanRequest {

    @NotNull(message = "Kitab nüsxəsi seçilməsi məcburidir")
    @Schema(description = "Borcalınacaq kitab nüsxəsinin ID-si", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bookCopyId;

    @NotNull(message = "Üzv seçilməsi məcburidir")
    @Schema(description = "Borcalan üzvün ID-si", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long memberId;

    @NotNull(message = "Qayıtma tarixi boş ola bilməz")
    @Schema(description = "Qayıtma tarixi (yyyy-MM-dd)", example = "2026-08-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dueDate;

}
