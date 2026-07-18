package az.library.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank(message = "Book title is required")
    @Size(max = 300, message = "Title must not exceed 300 characters")
    private String title;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 17, message = "ISBN must be between 10 and 17 characters")
    private String isbn;

    private Integer publicationYear;

    @Size(max = 50, message = "Edition must not exceed 50 characters")
    private String edition;

    private Integer pageCount;

    @Size(max = 50, message = "Language must not exceed 50 characters")
    private String language;

    @Size(max = 5000, message = "Summary must not exceed 5000 characters")
    private String summary;

    private Long publisherId;

    @NotNull(message = "At least one author is required")
    private Set<Long> authorIds;

    private Set<Long> categoryIds;
}
