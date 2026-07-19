package az.library.library.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kateqoriyanın ətraflı cavabı")
public class CategoryDetailedResponse {

    @Schema(description = "Kateqoriyanın ID-si", example = "1")
    private Long id;

    @Schema(description = "Kateqoriya adı", example = "Bədii ədəbiyyat")
    private String name;

    @Schema(description = "Təsvir")
    private String description;

    @Schema(description = "Üst kateqoriya ID-si (boş = kök kateqoriya)", example = "null")
    private Long parentId;

    @Schema(description = "Üst kateqoriya adı", example = "Ədəbiyyat")
    private String parentName;

    @Schema(description = "Alt kateqoriyalar")
    private List<CategorySummaryResponse> subcategories;

    @Schema(description = "Yaradılma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Yenilənmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime updatedAt;

}
