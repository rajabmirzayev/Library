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
@Schema(description = "Nəşriyyatın qısa siyahı cavabı")
public class PublisherSummaryResponse {

    @Schema(description = "Nəşriyyatın ID-si", example = "1")
    private Long id;

    @Schema(description = "Nəşriyyat adı", example = "Qanun Nəşriyyatı")
    private String name;

}
