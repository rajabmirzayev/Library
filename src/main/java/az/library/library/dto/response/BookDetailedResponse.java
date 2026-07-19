package az.library.library.dto.response;

import az.library.library.enums.BookStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kitabın ətraflı cavabı")
public class BookDetailedResponse {

    @Schema(description = "Kitabın ID-si", example = "1")
    private Long id;

    @Schema(description = "Kitabın adı", example = "Üçippyşq")
    private String title;

    @Schema(description = "ISBN nömrəsi", example = "978-0-13-468599-1")
    private String isbn;

    @Schema(description = "Nəşr ili", example = "2024")
    private Integer publicationYear;

    @Schema(description = "Nəşr redaktiyası", example = "1-ci nəşr")
    private String edition;

    @Schema(description = "Səhifə sayı", example = "350")
    private Integer pageCount;

    @Schema(description = "Dil", example = "Azərbaycan")
    private String language;

    @Schema(description = "Xülasə")
    private String summary;

    @Schema(description = "Kitabın statusu", example = "AVAILABLE")
    private BookStatus status;

    @Schema(description = "Nəşriyyat ID-si", example = "1")
    private Long publisherId;

    @Schema(description = "Nəşriyyatın adı", example = "Qanun Nəşriyyatı")
    private String publisherName;

    @Schema(description = "Müəlliflərin adları")
    private Set<String> authorNames;

    @Schema(description = "Kateqoriyaların adları")
    private Set<String> categoryNames;

    @Schema(description = "Yaradılma tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Yenilənmə tarixi", example = "2026-07-15T10:30:00")
    private LocalDateTime updatedAt;

}
