package az.library.library.dto.response;

import az.library.library.enums.ReservationStatus;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationSummaryResponse {

    private Long id;
    private LocalDate expiryDate;
    private ReservationStatus status;
    private String bookTitle;
    private Integer queuePosition;

}
