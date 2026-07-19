package az.library.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Kitab nüsxəsi məlumatlarını yeniləmək üçün sorğu")
public class UpdateBookCopyRequest {

    @Size(max = 50, message = "Barkod nömrəsi maksimum 50 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni barkod nömrəsi", example = "BK-2026-002")
    private String barcode;

    @Size(max = 100, message = "Raf yeri maksimum 100 simvoldan ibarət ola bilər")
    @Schema(description = "Yeni raf yeri kodu", example = "B-1-05")
    private String shelfLocation;

    @Schema(description = "Yeni vəziyyət (NEW / GOOD / FAIR / POOR / DAMAGED)", example = "GOOD")
    private String condition;

    @Schema(description = "Yeni aid olduğu kitabın ID-si", example = "2")
    private Long bookId;

}
