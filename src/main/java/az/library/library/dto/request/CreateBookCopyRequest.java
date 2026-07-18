package az.library.library.dto.request;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Size(max = 50)
    private String barcode;

    @Size(max = 100)
    private String shelfLocation;

    private String condition;

    private Long bookId;

}
