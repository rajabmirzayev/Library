package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Yeni istifadəçi yaratmaq üçün sorğu")
public class CreateUserRequest {

    @NotBlank(message = "İstifadəçi adı boş ola bilməz")
    @Size(min = 3, max = 50, message = "İstifadəçi adı 3-50 simvol aralığında olmalıdır")
    @Schema(description = "İstifadəçi adı", example = "ali_hesenov", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "E-poçt ünvanı boş ola bilməz")
    @Email(message = "Düzgün e-poçt ünvanı daxil edin")
    @Size(max = 150, message = "E-poçt ünvanı maksimum 150 simvoldan ibarət ola bilər")
    @Schema(description = "E-poçt ünvanı", example = "ali@library.az", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Şifrə boş ola bilməz")
    @Size(min = 6, message = "Şifrə minimum 6 simvoldan ibarət olmalıdır")
    @Schema(description = "Şifrə", example = "Guclu123!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "İstifadəçi rolu (ADMIN / LIBRARIAN / USER)", example = "USER")
    private String role;

}
