package az.library.library.dto.request;

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
@Schema(description = "Rezervasiya məlumatlarını yeniləmək üçün sorğu")
public class UpdateReservationRequest {

    @Schema(description = "Yeni bitmə tarixi (yyyy-MM-dd)", example = "2026-09-01")
    private LocalDate expiryDate;

}
