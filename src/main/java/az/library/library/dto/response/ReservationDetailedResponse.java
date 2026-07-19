package az.library.library.dto.response;

import az.library.library.enums.ReservationStatus;
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
@Schema(description = "Rezervasiyanın ətraflı cavabı")
public class ReservationDetailedResponse {

    @Schema(description = "Rezervasiyanın ID-si", example = "1")
    private Long id;

    @Schema(description = "Rezervasiya tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime reservationDate;

    @Schema(description = "Bitmə tarixi", example = "2026-08-01")
    private LocalDate expiryDate;

    @Schema(description = "Növbə mövqeyi", example = "1")
    private Integer queuePosition;

    @Schema(description = "Rezervasiya statusu", example = "PENDING")
    private ReservationStatus status;

    @Schema(description = "Kitabın ID-si", example = "1")
    private Long bookId;

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
