package az.library.library.dto.response;

import az.library.library.enums.BookStatus;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailedResponse {

    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private String edition;
    private Integer pageCount;
    private String language;
    private String summary;
    private BookStatus status;
    private Long publisherId;
    private String publisherName;
    private Set<String> authorNames;
    private Set<String> categoryNames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
