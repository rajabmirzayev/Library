package az.library.library.dto.response;

import az.library.library.enums.FineStatus;
import az.library.library.enums.FineType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Cərimənin qısa siyahı cavabı")
public class FineSummaryResponse {

    @Schema(description = "Cərimənin ID-si", example = "1")
    private Long id;

    @Schema(description = "Məbləğ (AZN)", example = "25.50")
    private BigDecimal amount;

    @Schema(description = "Cərimə növü", example = "LATE_RETURN")
    private FineType type;

    @Schema(description = "Status", example = "PENDING")
    private FineStatus status;

    @Schema(description = "Üzvün adı soyadı", example = "Əli Həsənov")
    private String memberName;

}
