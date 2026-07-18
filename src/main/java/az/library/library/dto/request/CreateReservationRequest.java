package az.library.library.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReservationRequest {

    @NotNull(message = "Kitab seçilməsi məcburidir")
    private Long bookId;

    @NotNull(message = "Üzv seçilməsi məcburidir")
    private Long memberId;

    @NotNull(message = "Bitmə tarixi boş ola bilməz")
    private LocalDate expiryDate;

}
