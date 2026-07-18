package az.library.library.dto.response;

import az.library.library.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookSummaryResponse {

    private Long id;
    private String title;
    private String isbn;
    private BookStatus status;
    private String publisherName;
    private String authorNames;

}
