package az.library.library.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCategoryRequest {

    @Size(max = 100, message = "Kateqoriya adı maksimum 100 simvoldan ibarət ola bilər")
    private String name;

    @Size(max = 500, message = "Təsvir maksimum 500 simvoldan ibarət ola bilər")
    private String description;

    private Long parentId;

}
