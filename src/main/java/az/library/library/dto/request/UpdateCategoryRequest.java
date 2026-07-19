package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kateqoriya məlumatlarını yeniləmək üçün sorğu")
public class UpdateCategoryRequest {

    @Size(max = 100, message = "Kateqoriya adı maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni kateqoriya adı", example = "Elmi ədəbiyyat")
    private String name;

    @Size(max = 500, message = "Təsvir maksimum 500 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni təsvir")
    private String description;

    @Schema(description = "Yeni üst kateqoriya ID-si", example = "1")
    private Long parentId;

}
