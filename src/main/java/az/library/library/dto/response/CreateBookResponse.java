package az.library.library.dto.response;

import az.library.library.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookResponse {

    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private String edition;
    private Integer pageCount;
    private String language;
    private String summary;
    private BookStatus status;
    private String publisherName;
    private Set<String> authorNames;
    private Set<String> categoryNames;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
