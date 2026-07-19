package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Yeni müəllif yaratmaq üçün sorğu")
public class CreateAuthorRequest {

    @NotBlank(message = "Müəllifin adı boş ola bilməz")
    @Size(max = 100, message = "Müəllifin adı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Müəllifin adı", example = "Çingiz", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = "Müəllifin soyadı boş ola bilməz")
    @Size(max = 100, message = "Müəllifin soyadı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Müəllifin soyadı", example = "Abdullayev", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Size(max = 2000, message = "Biografiya maksimum 2000 simvoldan ibarət ola bilər")
    @Schema(description = "Müəllif haqqında qısa bioqrafiya")
    private String biography;

    @Schema(description = "Doğum tarixi (yyyy-MM-dd)", example = "1977-02-12")
    private LocalDate birthDate;

    @Size(max = 100, message = "Milliyət adı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Müəllifin milliyəti", example = "Azərbaycan")
    private String nationality;

}
