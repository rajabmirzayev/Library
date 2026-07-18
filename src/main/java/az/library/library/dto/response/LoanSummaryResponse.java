package az.library.library.dto.response;

import az.library.library.enums.LoanStatus;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanSummaryResponse {

    private Long id;
    private LocalDate dueDate;
    private LoanStatus status;
    private String bookTitle;
    private String memberName;

}
