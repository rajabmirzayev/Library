package az.library.library.entity;

import az.library.library.enums.Gender;
import az.library.library.enums.MemberStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "members", indexes = {
        @Index(name = "idx_member_email", columnList = "email", unique = true),
        @Index(name = "idx_member_membership_number", columnList = "membership_number", unique = true),
        @Index(name = "idx_member_phone", columnList = "phone"),
        @Index(name = "idx_member_status", columnList = "status")
})
public class Member extends BaseEntity {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Column(name = "first_name", nullable = false, length = 100)
    @ToString.Include
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Column(name = "last_name", nullable = false, length = 100)
    @ToString.Include
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    @Column(nullable = false, unique = true, length = 150)
    @ToString.Include
    private String email;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    @Column(length = 20)
    @ToString.Include
    private String phone;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    @Column(length = 500)
    @ToString.Include
    private String address;

    @NotBlank(message = "Membership number is required")
    @Size(max = 30, message = "Membership number must not exceed 30 characters")
    @Column(name = "membership_number", nullable = false, unique = true, length = 30)
    @ToString.Include
    private String membershipNumber;

    @Column(name = "membership_date", nullable = false)
    @ToString.Include
    private LocalDateTime membershipDate;

    @Column(name = "date_of_birth")
    @ToString.Include
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @ToString.Include
    private Gender gender;

    @NotNull(message = "Member status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    @ToString.Include
    private MemberStatus status = MemberStatus.ACTIVE;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Loan> loans = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Fine> fines = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member other = (Member) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
