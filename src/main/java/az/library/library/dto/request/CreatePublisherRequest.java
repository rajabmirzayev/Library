package az.library.library.dto.request;

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
public class CreatePublisherRequest {

    @NotBlank(message = "Nəşriyyat adı boş ola bilməz")
    @Size(max = 200, message = "Nəşriyyat adı maksimum 200 simvoldan ibarət ola bilər")
    private String name;

    @Size(max = 500, message = "Ünvan maksimum 500 simvoldan ibarət ola bilər")
    private String address;

    @Size(max = 20, message = "Telefon nömrəsi maksimum 20 simvoldan ibarət ola bilər")
    private String phone;

    @Email(message = "Düzgün e-poçt ünvanı daxil edin")
    @Size(max = 150, message = "E-poçt ünvanı maksimum 150 simvoldan ibarət ola bilər")
    private String email;

    @Size(max = 200, message = "Vebsayt ünvanı maksimum 200 simvoldan ibarət ola bilər")
    private String website;

}
