package az.library.library.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Müəllifin ətraflı cavabı")
public class AuthorDetailedResponse {

    @Schema(description = "Müəllifin ID-si", example = "1")
    private Long id;

    @Schema(description = "Adı", example = "Çingiz")
    private String firstName;

    @Schema(description = "Soyadı", example = "Abdullayev")
    private String lastName;

    @Schema(description = "Bioqrafiya")
    private String biography;

    @Schema(description = "Doğum tarixi", example = "1977-02-12")
    private LocalDate birthDate;

    @Schema(description = "Milliyət", example = "Azərbaycan")
    private String nationality;

    @Schema(description = "Müəllifin kitablarının adları")
    private Set<String> bookTitles;

    @Schema(description = "Yaradılma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Yenilənmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime updatedAt;

}
