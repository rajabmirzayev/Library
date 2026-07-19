package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Müəllif məlumatlarını yeniləmək üçün sorğu")
public class UpdateAuthorRequest {

    @Size(max = 100, message = "Müəllifin adı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni ad", example = "Çingiz")
    private String firstName;

    @Size(max = 100, message = "Müəllifin soyadı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni soyad", example = "Abdullayev")
    private String lastName;

    @Size(max = 2000, message = "Biografiya maksimum 2000 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni bioqrafiya")
    private String biography;

    @Schema(description = "Yeni doğum tarixi (yyyy-MM-dd)", example = "1977-02-12")
    private LocalDate birthDate;

    @Size(max = 100, message = "Milliyət adı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni milliyət", example = "Azərbaycan")
    private String nationality;

}
