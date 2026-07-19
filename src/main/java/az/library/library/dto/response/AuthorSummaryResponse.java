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
@Schema(description = "Müəllifin qısa siyahı cavabı")
public class AuthorSummaryResponse {

    @Schema(description = "Müəllifin ID-si", example = "1")
    private Long id;

    @Schema(description = "Adı", example = "Çingiz")
    private String firstName;

    @Schema(description = "Soyadı", example = "Abdullayev")
    private String lastName;

    @Schema(description = "Milliyət", example = "Azərbaycan")
    private String nationality;

}
