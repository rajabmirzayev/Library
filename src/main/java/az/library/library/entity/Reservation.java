package az.library.library.entity;

import az.library.library.enums.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "reservations", indexes = {
        @Index(name = "idx_reservation_status", columnList = "status"),
        @Index(name = "idx_reservation_member_id", columnList = "member_id"),
        @Index(name = "idx_reservation_book_id", columnList = "book_id"),
        @Index(name = "idx_reservation_expiry_date", columnList = "expiry_date")
})
public class Reservation extends BaseEntity {

    @NotNull(message = "Reservation date is required")
    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @NotNull(message = "Expiry date is required")
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "queue_position")
    private Integer queuePosition;

    @NotNull(message = "Reservation status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
