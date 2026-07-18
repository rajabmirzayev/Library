package az.library.library.dto.response;

import az.library.library.enums.BookCopyCondition;
import az.library.library.enums.BookCopyStatus;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCopyDetailedResponse {

    private Long id;
    private String barcode;
    private String shelfLocation;
    private BookCopyCondition condition;
    private BookCopyStatus status;
    private Long bookId;
    private String bookTitle;
    private String bookIsbn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
