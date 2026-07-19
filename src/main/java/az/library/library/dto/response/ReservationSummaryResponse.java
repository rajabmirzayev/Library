package az.library.library.dto.response;

import az.library.library.enums.ReservationStatus;
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
@Schema(description = "Rezervasiyanın qısa siyahı cavabı")
public class ReservationSummaryResponse {

    @Schema(description = "Rezervasiyanın ID-si", example = "1")
    private Long id;

    @Schema(description = "Bitmə tarixi", example = "2026-08-01")
    private LocalDate expiryDate;

    @Schema(description = "Status", example = "PENDING")
    private ReservationStatus status;

    @Schema(description = "Kitabın adı", example = "Üçippyşq")
    private String bookTitle;

    @Schema(description = "Növbə mövqeyi", example = "1")
    private Integer queuePosition;

}
