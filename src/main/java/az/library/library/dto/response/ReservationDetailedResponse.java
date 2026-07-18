package az.library.library.dto.response;

import az.library.library.enums.ReservationStatus;

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
public class ReservationDetailedResponse {

    private Long id;
    private LocalDateTime reservationDate;
    private LocalDate expiryDate;
    private Integer queuePosition;
    private ReservationStatus status;
    private Long bookId;
    private String bookTitle;
    private Long memberId;
    private String memberName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
