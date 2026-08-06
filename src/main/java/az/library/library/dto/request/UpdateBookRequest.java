package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kitab məlumatlarını yeniləmək üçün sorğu")
public class UpdateBookRequest {

    @Size(max = 300, message = "Kitab adı maksimum 300 simvoldan ibarət ola bilər")
    @Schema(description = "Kitabın yeni adı", example = "Yeni ad")
    private String title;

    @Size(min = 10, max = 17, message = "ISBN nömrəsi 10-17 simvol aralığında olmalıdır")
    @Schema(description = "Yeni ISBN nömrəsi", example = "978-0-13-468599-1")
    private String isbn;

    @Schema(description = "Yeni nəşr ili", example = "2025")
    private Integer publicationYear;

    @Size(max = 50, message = "Nəşr adı maksimum 50 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni nəşr redaktiyası")
    private String edition;

    @Schema(description = "Yeni səhifə sayı", example = "400")
    private Integer pageCount;

    @Size(max = 50, message = "Dil adı maksimum 50 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni dil")
    private String language;

    @Size(max = 5000, message = "Xülasə maksimum 5000 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni xülasə")
    private String summary;

    @DecimalMin(value = "0.0", message = "Qiymət mənfi ola bilməz")
    @Digits(integer = 8, fraction = 2, message = "Qiymət maksimum 8 tam və 2 kəsr rəqəmdən ibarət ola bilər")
    @Schema(description = "Yeni qiymət (AZN)", example = "24.99")
    private BigDecimal price;

    @Schema(description = "Yeni nəşriyyat ID-si (0 = nəşriyyatı sil)", example = "1")
    private Long publisherId;

    @Schema(description = "Yeni müəllif ID-ləri dəsti")
    private Set<Long> authorIds;

    @Schema(description = "Yeni kateqoriya ID-ləri dəsti")
    private Set<Long> categoryIds;

}
