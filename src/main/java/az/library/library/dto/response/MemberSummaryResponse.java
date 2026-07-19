package az.library.library.dto.response;

import az.library.library.enums.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Üzvün qısa siyahı cavabı")
public class MemberSummaryResponse {

    @Schema(description = "Üzvün ID-si", example = "1")
    private Long id;

    @Schema(description = "Adı", example = "Əli")
    private String firstName;

    @Schema(description = "Soyadı", example = "Həsənov")
    private String lastName;

    @Schema(description = "E-poçt ünvanı", example = "ali@library.az")
    private String email;

    @Schema(description = "Status", example = "ACTIVE")
    private MemberStatus status;

}
