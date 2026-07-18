package az.library.library.entity;

import az.library.library.enums.FineStatus;
import az.library.library.enums.FineType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "fines", indexes = {
        @Index(name = "idx_fine_status", columnList = "status"),
        @Index(name = "idx_fine_member_id", columnList = "member_id"),
        @Index(name = "idx_fine_loan_id", columnList = "loan_id")
})
public class Fine extends BaseEntity {

    @NotNull(message = "Fine amount is required")
    @Positive(message = "Fine amount must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(length = 500)
    private String reason;

    @NotNull(message = "Fine type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FineType type;

    @NotNull(message = "Fine status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private FineStatus status = FineStatus.PENDING;

    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issuedDate;

    @Column(name = "paid_date")
    private LocalDateTime paidDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
