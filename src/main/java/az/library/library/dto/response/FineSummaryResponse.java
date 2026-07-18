package az.library.library.dto.response;

import az.library.library.enums.FineStatus;
import az.library.library.enums.FineType;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FineSummaryResponse {

    private Long id;
    private BigDecimal amount;
    private FineType type;
    private FineStatus status;
    private String memberName;

}
