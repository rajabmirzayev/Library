package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Nəşriyyat məlumatlarını yeniləmək üçün sorğu")
public class UpdatePublisherRequest {

    @Size(max = 200, message = "Nəşriyyat adı maksimum 200 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni nəşriyyat adı", example = "Qanun Nəşriyyatı")
    private String name;

    @Size(max = 500, message = "Ünvan maksimum 500 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni ünvan")
    private String address;

    @Size(max = 20, message = "Telefon nömrəsi maksimum 20 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni telefon nömrəsi", example = "+994501234567")
    private String phone;

    @Email(message = "Düzgün e-poçt ünvanı daxil edin")
    @Size(max = 150, message = "E-poçt ünvanı maksimum 150 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni e-poçt ünvanı", example = "info@qanun.az")
    private String email;

    @Size(max = 200, message = "Vebsayt ünvanı maksimum 200 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni vebsayt ünvanı", example = "https://qanun.az")
    private String website;

}
