package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Yeni kitab nüsxəsi yaratmaq üçün sorğu")
public class CreateBookCopyRequest {

    @NotBlank(message = "Barkod nömrəsi boş ola bilməz")
    @Size(max = 50, message = "Barkod nömrəsi maksimum 50 simvoldan ibarət ola bilər")
    @Schema(description = "Kitab nüsxəsinin barkod nömrəsi", example = "BK-2026-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String barcode;

    @Size(max = 100, message = "Raf yeri maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Raf yeri kodu", example = "A-3-12")
    private String shelfLocation;

    @Schema(description = "Vəziyyəti (NEW / GOOD / FAIR / POOR / DAMAGED)", example = "NEW")
    private String condition;

    @NotNull(message = "Kitab seçilməsi məcburidir")
    @Schema(description = " Aid olduğu kitabın ID-si", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bookId;

}
