package az.library.library.dto.request;

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
public class CreateMemberRequest {

    @NotBlank(message = "Üzvün adı boş ola bilməz")
    @Size(max = 100, message = "Üzvün adı maksimum 100 simvoldan ibarət ola bilər")
    private String firstName;

    @NotBlank(message = "Üzvün soyadı boş ola bilməz")
    @Size(max = 100, message = "Üzvün soyadı maksimum 100 simvoldan ibarət ola bilər")
    private String lastName;

    @NotBlank(message = "E-poçt ünvanı boş ola bilməz")
    @Email(message = "Düzgün e-poçt ünvanı daxil edin")
    @Size(max = 150, message = "E-poçt ünvanı maksimum 150 simvoldan ibarət ola bilər")
    private String email;

    @Size(max = 20, message = "Telefon nömrəsi maksimum 20 simvoldan ibarət ola bilər")
    private String phone;

    @Size(max = 500, message = "Ünvan maksimum 500 simvoldan ibarət ola bilər")
    private String address;

    private LocalDate dateOfBirth;

    private String gender;

}
