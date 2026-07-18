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

    @NotNull
    private Long bookCopyId;

    @NotNull
    private Long memberId;

    @NotNull
    private LocalDate dueDate;

}
