package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Yeni nəşriyyat yaratmaq üçün sorğu")
public class CreatePublisherRequest {

    @NotBlank(message = "Nəşriyyat adı boş ola bilməz")
    @Size(max = 200, message = "Nəşriyyat adı maksimum 200 simvoldan ibarət ola bilər")
    @Schema(description = "Nəşriyyatın adı", example = "Qanun Nəşriyyatı", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 500, message = "Ünvan maksimum 500 simvoldan ibarət ola bilər")
    @Schema(description = "Nəşriyyatın ünvanı", example = "Bakı şəhəri, Nizami küç. 12")
    private String address;

    @Size(max = 20, message = "Telefon nömrəsi maksimum 20 simvoldan ibarət ola bilər")
    @Schema(description = "Telefon nömrəsi", example = "+994501234567")
    private String phone;

    @Email(message = "Düzgün e-poçt ünvanı daxil edin")
    @Size(max = 150, message = "E-poçt ünvanı maksimum 150 simvoldan ibarət ola bilər")
    @Schema(description = "E-poçt ünvanı", example = "info@qanun.az")
    private String email;

    @Size(max = 200, message = "Vebsayt ünvanı maksimum 200 simvoldan ibarət ola bilər")
    @Schema(description = "Vebsayt ünvanı", example = "https://qanun.az")
    private String website;

}
