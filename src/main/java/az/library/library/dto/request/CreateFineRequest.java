package az.library.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFineRequest {

    @NotNull(message = "Cərimə məbləği boş ola bilməz")
    @Positive(message = "Cərimə məbləği müsbət olmalıdır")
    private BigDecimal amount;

    private String reason;

    @NotBlank(message = "Cərimə növü boş ola bilməz")
    private String type;

    private Long loanId;

    @NotNull(message = "Üzv seçilməsi məcburidir")
    private Long memberId;

}
