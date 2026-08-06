package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Yeni kitab yaratmaq üçün sorğu")
public class CreateBookRequest {

    @NotBlank(message = "Kitab adı boş ola bilməz")
    @Size(max = 300, message = "Kitab adı maksimum 300 simvoldan ibarət ola bilər")
    @Schema(description = "Kitabın adı", example = "Üçippyşq", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank(message = "ISBN nömrəsi boş ola bilməz")
    @Size(min = 10, max = 17, message = "ISBN nömrəsi 10-17 simvol aralığında olmalıdır")
    @Schema(description = "ISBN nömrəsi (10-17 simvol)", example = "978-0-13-468599-1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isbn;

    @Schema(description = "Nəşr ili", example = "2024")
    private Integer publicationYear;

    @Size(max = 50, message = "Nəşr adı maksimum 50 simvoldan ibarət ola bilər")
    @Schema(description = "Nəşr redaktiyası", example = "1-ci nəşr")
    private String edition;

    @Schema(description = "Səhifə sayı", example = "350")
    private Integer pageCount;

    @Size(max = 50, message = "Dil adı maksimum 50 simvoldan ibarət ola bilər")
    @Schema(description = "Kitabın dili", example = "Azərbaycan")
    private String language;

    @Size(max = 5000, message = "Xülasə maksimum 5000 simvoldan ibarət ola bilər")
    @Schema(description = "Kitabın qısa xülasəsi")
    private String summary;

    @DecimalMin(value = "0.0", message = "Qiymət mənfi ola bilməz")
    @Digits(integer = 8, fraction = 2, message = "Qiymət maksimum 8 tam və 2 kəsr rəqəmdən ibarət ola bilər")
    @Schema(description = "Kitabın qiyməti (AZN)", example = "19.99")
    private BigDecimal price;

    @Schema(description = "Nəşriyyat ID-si", example = "1")
    private Long publisherId;

    @NotNull(message = "Müəllif seçilməsi məcburidir")
    @Schema(description = "Müəllif ID-ləri dəsti", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Long> authorIds;

    @Schema(description = "Kateqoriya ID-ləri dəsti")
    private Set<Long> categoryIds;

}
