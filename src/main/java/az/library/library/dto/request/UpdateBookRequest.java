package az.library.library.dto.request;

import jakarta.validation.constraints.Size;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookRequest {

    @Size(max = 300)
    private String title;

    @Size(min = 10, max = 17)
    private String isbn;

    private Integer publicationYear;

    @Size(max = 50)
    private String edition;

    private Integer pageCount;

    @Size(max = 50)
    private String language;

    @Size(max = 5000)
    private String summary;

    private Long publisherId;

    private Set<Long> authorIds;

    private Set<Long> categoryIds;

}
