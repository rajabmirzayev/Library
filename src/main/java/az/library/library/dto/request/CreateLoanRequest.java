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
public class CreateLoanRequest {

    @NotNull(message = "Kitab nüsxəsi seçilməsi məcburidir")
    private Long bookCopyId;

    @NotNull(message = "Üzv seçilməsi məcburidir")
    private Long memberId;

    @NotNull(message = "Qayıtma tarixi boş ola bilməz")
    private LocalDate dueDate;

}
