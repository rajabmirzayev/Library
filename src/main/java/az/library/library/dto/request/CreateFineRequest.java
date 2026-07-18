package az.library.library.dto.request;

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

    @NotNull
    @Positive
    private BigDecimal amount;

    private String reason;

    @NotNull
    private String type;

    private Long loanId;

    @NotNull
    private Long memberId;

}
