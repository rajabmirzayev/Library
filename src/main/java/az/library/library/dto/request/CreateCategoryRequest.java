package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Yeni kateqoriya yaratmaq üçün sorğu")
public class CreateCategoryRequest {

    @NotBlank(message = "Kateqoriya adı boş ola bilməz")
    @Size(max = 100, message = "Kateqoriya adı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Kateqoriya adı", example = "Bədii ədəbiyyat", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(max = 500, message = "Təsvir maksimum 500 simvoldan ibarət ola bilər")
    @Schema(description = "Kateqoriyanın təsviri")
    private String description;

    @Schema(description = "Üst kateqoriya ID-si (boş = kök kateqoriya)", example = "null")
    private Long parentId;

}
