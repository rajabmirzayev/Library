package az.library.library.dto.request;

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
public class CreateAuthorRequest {

    @NotBlank(message = "Müəllifin adı boş ola bilməz")
    @Size(max = 100, message = "Müəllifin adı maksimum 100 simvoldan ibarət ola bilər")
    private String firstName;

    @NotBlank(message = "Müəllifin soyadı boş ola bilməz")
    @Size(max = 100, message = "Müəllifin soyadı maksimum 100 simvoldan ibarət ola bilər")
    private String lastName;

    @Size(max = 2000, message = "Biografiya maksimum 2000 simvoldan ibarət ola bilər")
    private String biography;

    private LocalDate birthDate;

    @Size(max = 100, message = "Milliyət adı maksimum 100 simvoldan ibarət ola bilər")
    private String nationality;

}
