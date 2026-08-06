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
@Schema(description = "İstifadəçi məlumatlarını yeniləmək üçün sorğu")
public class UpdateUserRequest {

    @Size(min = 3, max = 50, message = "İstifadəçi adı 3-50 simvol aralığında olmalıdır")
    @Schema(description = "Yeni istifadəçi adı", example = "ali_hesenov")
    private String username;

    @Email(message = "Düzgün e-poçt ünvanı daxil edin")
    @Size(max = 150, message = "E-poçt ünvanı maksimum 150 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni e-poçt ünvanı", example = "ali@library.az")
    private String email;

    @Size(min = 6, message = "Şifrə minimum 6 simvoldan ibarət olmalıdır")
    @Schema(description = "Yeni şifrə", example = "YeniGuclu456!")
    private String password;

    @Schema(description = "Yeni istifadəçi rolu (ADMIN / LIBRARIAN / USER)", example = "LIBRARIAN")
    private String role;

}
