package az.library.library.dto.response;

import az.library.library.enums.FineStatus;
import az.library.library.enums.FineType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FineDetailedResponse {

    private Long id;
    private BigDecimal amount;
    private String reason;
    private FineType type;
    private FineStatus status;
    private LocalDateTime issuedDate;
    private LocalDateTime paidDate;
    private Long loanId;
    private Long memberId;
    private String memberName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
