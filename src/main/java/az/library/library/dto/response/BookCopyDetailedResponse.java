package az.library.library.dto.response;

import az.library.library.enums.BookCopyCondition;
import az.library.library.enums.BookCopyStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kitab nüsxəsinin ətraflı cavabı")
public class BookCopyDetailedResponse {

    @Schema(description = "Kitab nüsxəsinin ID-si", example = "1")
    private Long id;

    @Schema(description = "Barkod nömrəsi", example = "BK-2026-001")
    private String barcode;

    @Schema(description = "Raf yeri kodu", example = "A-3-12")
    private String shelfLocation;

    @Schema(description = "Vəziyyəti", example = "NEW")
    private BookCopyCondition condition;

    @Schema(description = "Statusu", example = "AVAILABLE")
    private BookCopyStatus status;

    @Schema(description = "Aid olduğu kitabın ID-si", example = "1")
    private Long bookId;

    @Schema(description = "Kitabın adı", example = "Üçippyşq")
    private String bookTitle;

    @Schema(description = "Kitabın ISBN-i", example = "978-0-13-468599-1")
    private String bookIsbn;

    @Schema(description = "Yaradılma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Yenilənmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime updatedAt;

}
