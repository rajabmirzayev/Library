package az.library.library.dto.response;

import az.library.library.enums.MemberStatus;
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
@Schema(description = "Üzvün ətraflı cavabı")
public class MemberDetailedResponse {

    @Schema(description = "Üzvün ID-si", example = "1")
    private Long id;

    @Schema(description = "Adı", example = "Əli")
    private String firstName;

    @Schema(description = "Soyadı", example = "Həsənov")
    private String lastName;

    @Schema(description = "E-poçt ünvanı", example = "ali@library.az")
    private String email;

    @Schema(description = "Telefon nömrəsi", example = "+994501234567")
    private String phone;

    @Schema(description = "Ünvan")
    private String address;

    @Schema(description = "Üzvlük nömrəsi", example = "MEM-1721000000000")
    private String membershipNumber;

    @Schema(description = "Üzvlük tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime membershipDate;

    @Schema(description = "Doğum tarixi", example = "1995-06-15")
    private LocalDate dateOfBirth;

    @Schema(description = "Cins", example = "MALE")
    private String gender;

    @Schema(description = "Üzv statusu", example = "ACTIVE")
    private MemberStatus status;

    @Schema(description = "Aktiv borcalmaların sayı", example = "2")
    private int activeLoanCount;

    @Schema(description = "Gözləyən cərimələrin sayı", example = "0")
    private int pendingFineCount;

    @Schema(description = "Yaradılma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Yenilənmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime updatedAt;

}
