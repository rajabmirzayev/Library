package az.library.library.entity;

import az.library.library.enums.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "loans", indexes = {
        @Index(name = "idx_loan_status", columnList = "status"),
        @Index(name = "idx_loan_due_date", columnList = "due_date"),
        @Index(name = "idx_loan_member_id", columnList = "member_id"),
        @Index(name = "idx_loan_book_copy_id", columnList = "book_copy_id")
})
public class Loan extends BaseEntity {

    @NotNull(message = "Loan date is required")
    @Column(name = "loan_date", nullable = false)
    private LocalDateTime loanDate;

    @NotNull(message = "Due date is required")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @NotNull(message = "Loan status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private LoanStatus status = LoanStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_copy_id", nullable = false)
    private BookCopy bookCopy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Fine> fines = new ArrayList<>();
}
