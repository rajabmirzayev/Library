package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Yeni üzv yaratmaq üçün sorğu")
public class CreateMemberRequest {

    @NotBlank(message = "Üzvün adı boş ola bilməz")
    @Size(max = 100, message = "Üzvün adı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Üzvün adı", example = "Əli", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = "Üzvün soyadı boş ola bilməz")
    @Size(max = 100, message = "Üzvün soyadı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Üzvün soyadı", example = "Həsənov", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @NotBlank(message = "E-poçt ünvanı boş ola bilməz")
    @Email(message = "Düzgün e-poçt ünvanı daxil edin")
    @Size(max = 150, message = "E-poçt ünvanı maksimum 150 simvoldan ibarət ola bilər")
    @Schema(description = "E-poçt ünvanı", example = "ali@library.az", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Size(max = 20, message = "Telefon nömrəsi maksimum 20 simvoldan ibarət ola bilər")
    @Schema(description = "Telefon nömrəsi", example = "+994501234567")
    private String phone;

    @Size(max = 500, message = "Ünvan maksimum 500 simvoldan ibarət ola bilər")
    @Schema(description = "Ünvana dair məlumat")
    private String address;

    @Schema(description = "Doğum tarixi (yyyy-MM-dd)", example = "1995-06-15")
    private LocalDate dateOfBirth;

    @Schema(description = "Cins (MALE / FEMALE)", example = "MALE")
    private String gender;

}
