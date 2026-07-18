package az.library.library.dto.response;

import az.library.library.enums.BookCopyCondition;
import az.library.library.enums.BookCopyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCopySummaryResponse {

    private Long id;
    private String barcode;
    private BookCopyStatus status;
    private BookCopyCondition condition;

}
