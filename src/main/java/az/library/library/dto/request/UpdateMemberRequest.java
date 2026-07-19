package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Üzv məlumatlarını yeniləmək üçün sorğu")
public class UpdateMemberRequest {

    @Size(max = 100, message = "Üzvün adı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni ad", example = "Əli")
    private String firstName;

    @Size(max = 100, message = "Üzvün soyadı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni soyad", example = "Həsənov")
    private String lastName;

    @Email(message = "Düzgün e-poçt ünvanı daxil edin")
    @Size(max = 150, message = "E-poçt ünvanı maksimum 150 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni e-poçt ünvanı", example = "ali@library.az")
    private String email;

    @Size(max = 20, message = "Telefon nömrəsi maksimum 20 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni telefon nömrəsi", example = "+994509876543")
    private String phone;

    @Size(max = 500, message = "Ünvan maksimum 500 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni ünvan")
    private String address;

    @Schema(description = "Yeni doğum tarixi (yyyy-MM-dd)", example = "1995-06-15")
    private LocalDate dateOfBirth;

    @Schema(description = "Yeni cins (MALE / FEMALE)", example = "MALE")
    private String gender;

}
