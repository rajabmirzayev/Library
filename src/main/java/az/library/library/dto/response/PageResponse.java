package az.library.library.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Səhifələmə nəticəsi")
public class PageResponse<T> {

    @Schema(description = "Səhifədəki elementlər")
    private List<T> content;

    @Schema(description = "Cari səhifə nömrəsi (0-dan başlayır)", example = "0")
    @JsonProperty("page")
    private int pageNumber;

    @Schema(description = "Səhifə ölçüsü", example = "20")
    @JsonProperty("size")
    private int pageSize;

    @Schema(description = "Ümumi element sayı", example = "150")
    private long totalElements;

    @Schema(description = "Ümumi səhifə sayı", example = "8")
    private int totalPages;

    @Schema(description = "Son səhifədirmi", example = "false")
    private boolean last;

    public static <T> PageResponse<T> of(Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
