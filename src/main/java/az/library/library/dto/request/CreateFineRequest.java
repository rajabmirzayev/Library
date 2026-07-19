package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Yeni cərimə yaratmaq üçün sorğu")
public class CreateFineRequest {

    @NotNull(message = "Cərimə məbləği boş ola bilməz")
    @Positive(message = "Cərimə məbləği müsbət olmalıdır")
    @Schema(description = "Cərimə məbləği (AZN)", example = "25.50", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    @Schema(description = "Cərimə səbəbi", example = "Gecikmiş qayıtma")
    private String reason;

    @NotBlank(message = "Cərimə növü boş ola bilməz")
    @Schema(description = "Cərimə növü (LATE_RETURN / DAMAGED_BOOK / LOST_BOOK)", example = "LATE_RETURN", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Schema(description = "Əlaqədar borcalma ID-si", example = "1")
    private Long loanId;

    @NotNull(message = "Üzv seçilməsi məcburidir")
    @Schema(description = "Cərimə tətbiq edilən üzvün ID-si", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long memberId;

}
