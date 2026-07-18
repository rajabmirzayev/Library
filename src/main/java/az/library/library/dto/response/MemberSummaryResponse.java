package az.library.library.dto.response;

import az.library.library.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSummaryResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private MemberStatus status;

}
