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
@Schema(description = "Yeni rezervasiya yaratmaq üçün sorğu")
public class CreateReservationRequest {

    @NotNull(message = "Kitab seçilməsi məcburidir")
    @Schema(description = "Rezervasiya edilən kitabın ID-si", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bookId;

    @NotNull(message = "Üzv seçilməsi məcburidir")
    @Schema(description = "Rezervasiya edən üzvün ID-si", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long memberId;

    @NotNull(message = "Bitmə tarixi boş ola bilməz")
    @Schema(description = "Rezervasiya bitmə tarixi (yyyy-MM-dd)", example = "2026-08-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate expiryDate;

}
