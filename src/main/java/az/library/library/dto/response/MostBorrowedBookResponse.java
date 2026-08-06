package az.library.library.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ən çox icarəyə götürülən kitab")
public record MostBorrowedBookResponse(
        @Schema(description = "Kitab ID-si", example = "1")
        Long bookId,

        @Schema(description = "Kitabın adı", example = "Üçippyşq")
        String bookTitle,

        @Schema(description = "ISBN nömrəsi", example = "978-0-13-468599-1")
        String isbn,

        @Schema(description = "İcarə (kredit) sayı", example = "47")
        Long borrowCount,

        @Schema(description = "Müəllif adları (vergüllə)", example = "Çingiz Abdullayev, Elxan Elatlı")
        String authorNames
) {
}
