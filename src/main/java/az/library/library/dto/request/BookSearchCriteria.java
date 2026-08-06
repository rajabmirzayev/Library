package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.Parameter;

import java.math.BigDecimal;

public record BookSearchCriteria(
        @Parameter(description = "Kitab adında axtarış (case-insensitive contains)", example = "Xəmsə")
        String title,

        @Parameter(description = "Müəllif ID-si ilə filtr", example = "1")
        Long authorId,

        @Parameter(description = "Kateqoriya ID-si ilə filtr", example = "2")
        Long categoryId,

        @Parameter(description = "Nəşriyyat ID-si ilə filtr", example = "1")
        Long publisherId,

        @Parameter(description = "ISBN ilə dəqiq axtarış", example = "978-9952-9999-1-2")
        String isbn,

        @Parameter(description = "Minimum qiymət (AZN)", example = "10.00")
        BigDecimal minPrice,

        @Parameter(description = "Maksimum qiymət (AZN)", example = "50.00")
        BigDecimal maxPrice,

        @Parameter(description = "Başlanğıc nəşr ili (daxil)", example = "2020")
        Integer startYear,

        @Parameter(description = "Son nəşr ili (daxil)", example = "2025")
        Integer endYear
) {
}
