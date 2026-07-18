package az.library.library.dto.response;

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
public class CategoryDetailedResponse {

    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private String parentName;
    private List<CategorySummaryResponse> subcategories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
