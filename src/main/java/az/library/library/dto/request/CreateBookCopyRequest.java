package az.library.library.dto.request;

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
public class CreateBookCopyRequest {

    @NotBlank(message = "Barkod nömrəsi boş ola bilməz")
    @Size(max = 50, message = "Barkod nömrəsi maksimum 50 simvoldan ibarət ola bilər")
    private String barcode;

    @Size(max = 100, message = "Raf yeri maksimum 100 simvoldan ibarət ola bilər")
    private String shelfLocation;

    private String condition;

    @NotNull(message = "Kitab seçilməsi məcburidir")
    private Long bookId;

}
