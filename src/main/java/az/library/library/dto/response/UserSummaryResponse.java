package az.library.library.dto.response;

import az.library.library.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "İstifadəçinin qısa siyahı cavabı")
public class UserSummaryResponse {

    @Schema(description = "İstifadəçinin ID-si", example = "1")
    private Long id;

    @Schema(description = "İstifadəçi adı", example = "ali_hesenov")
    private String username;

    @Schema(description = "E-poçt ünvanı", example = "ali@library.az")
    private String email;

    @Schema(description = "İstifadəçi rolu", example = "USER")
    private UserRole role;

}
