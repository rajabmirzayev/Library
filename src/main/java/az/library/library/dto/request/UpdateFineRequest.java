package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Cərimə məlumatlarını yeniləmək üçün sorğu")
public class UpdateFineRequest {

    @Positive(message = "Cərimə məbləği müsbət olmalıdır")
    @Schema(description = "Yeni cərimə məbləği (AZN)", example = "30.00")
    private BigDecimal amount;

    @Schema(description = "Yeni cərimə səbəbi", example = "Zədələnmiş kitab")
    private String reason;

}
