package az.library.library.dto.response;

import az.library.library.enums.BookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kitabın qısa siyahı cavabı")
public class BookSummaryResponse {

    @Schema(description = "Kitabın ID-si", example = "1")
    private Long id;

    @Schema(description = "Kitabın adı", example = "Üçippyşq")
    private String title;

    @Schema(description = "ISBN nömrəsi", example = "978-0-13-468599-1")
    private String isbn;

    @Schema(description = "Status", example = "AVAILABLE")
    private BookStatus status;

    @Schema(description = "Nəşriyyat adı", example = "Qanun Nəşriyyatı")
    private String publisherName;

    @Schema(description = "Müəllif adları (vergül ilə)", example = "Çingiz Abdullayev")
    private String authorNames;

}
