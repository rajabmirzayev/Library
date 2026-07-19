package az.library.library.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Nəşriyyatın ətraflı cavabı")
public class PublisherDetailedResponse {

    @Schema(description = "Nəşriyyatın ID-si", example = "1")
    private Long id;

    @Schema(description = "Nəşriyyat adı", example = "Qanun Nəşriyyatı")
    private String name;

    @Schema(description = "Ünvan", example = "Bakı şəhəri, Nizami küç. 12")
    private String address;

    @Schema(description = "Telefon nömrəsi", example = "+994501234567")
    private String phone;

    @Schema(description = "E-poçt ünvanı", example = "info@qanun.az")
    private String email;

    @Schema(description = "Vebsayt", example = "https://qanun.az")
    private String website;

    @Schema(description = "Nəşriyyata aid kitabların sayı", example = "25")
    private int bookCount;

    @Schema(description = "Yaradılma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Yenilənmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime updatedAt;

}
