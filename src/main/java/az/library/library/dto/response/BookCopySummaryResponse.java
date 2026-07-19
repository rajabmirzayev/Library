package az.library.library.dto.response;

import az.library.library.enums.BookCopyCondition;
import az.library.library.enums.BookCopyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kitab nüsxəsinin qısa siyahı cavabı")
public class BookCopySummaryResponse {

    @Schema(description = "Kitab nüsxəsinin ID-si", example = "1")
    private Long id;

    @Schema(description = "Barkod nömrəsi", example = "BK-2026-001")
    private String barcode;

    @Schema(description = "Status", example = "AVAILABLE")
    private BookCopyStatus status;

    @Schema(description = "Vəziyyət", example = "NEW")
    private BookCopyCondition condition;

}
