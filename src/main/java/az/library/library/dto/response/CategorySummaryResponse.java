package az.library.library.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kateqoriyanın qısa siyahı cavabı")
public class CategorySummaryResponse {

    @Schema(description = "Kateqoriyanın ID-si", example = "1")
    private Long id;

    @Schema(description = "Kateqoriya adı", example = "Bədii ədəbiyyat")
    private String name;

}
