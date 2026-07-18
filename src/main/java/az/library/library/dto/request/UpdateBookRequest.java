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

    @Size(max = 300, message = "Kitab adı maksimum 300 simvoldan ibarət ola bilər")
    private String title;

    @Size(min = 10, max = 17, message = "ISBN nömrəsi 10-17 simvol aralığında olmalıdır")
    private String isbn;

    private Integer publicationYear;

    @Size(max = 50, message = "Nəşr adı maksimum 50 simvoldan ibarət ola bilər")
    private String edition;

    private Integer pageCount;

    @Size(max = 50, message = "Dil adı maksimum 50 simvoldan ibarət ola bilər")
    private String language;

    @Size(max = 5000, message = "Xülasə maksimum 5000 simvoldan ibarət ola bilər")
    private String summary;

    private Long publisherId;

    private Set<Long> authorIds;

    private Set<Long> categoryIds;

}
