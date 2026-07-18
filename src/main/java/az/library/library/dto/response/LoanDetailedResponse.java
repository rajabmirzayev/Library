package az.library.library.dto.response;

import az.library.library.enums.LoanStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDetailedResponse {

    private Long id;
    private LocalDateTime loanDate;
    private LocalDate dueDate;
    private LocalDateTime returnDate;
    private LoanStatus status;
    private Long bookCopyId;
    private String bookBarcode;
    private String bookTitle;
    private Long memberId;
    private String memberName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
