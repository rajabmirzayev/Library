package az.library.library.dto.response;

import az.library.library.enums.MemberStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetailedResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String membershipNumber;
    private LocalDateTime membershipDate;
    private LocalDate dateOfBirth;
    private String gender;
    private MemberStatus status;
    private int activeLoanCount;
    private int pendingFineCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
