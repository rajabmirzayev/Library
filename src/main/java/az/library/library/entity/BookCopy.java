package az.library.library.entity;

import az.library.library.enums.BookCopyCondition;
import az.library.library.enums.BookCopyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "book_copies", indexes = {
        @Index(name = "idx_book_copy_barcode", columnList = "barcode", unique = true),
        @Index(name = "idx_book_copy_status", columnList = "status"),
        @Index(name = "idx_book_copy_book_id", columnList = "book_id")
})
public class BookCopy extends BaseEntity {

    @NotBlank(message = "Barcode is required")
    @Size(max = 50, message = "Barcode must not exceed 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    @ToString.Include
    private String barcode;

    @Size(max = 100, message = "Shelf location must not exceed 100 characters")
    @Column(name = "shelf_location", length = 100)
    @ToString.Include
    private String shelfLocation;

    @NotNull(message = "Copy condition is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    @ToString.Include
    private BookCopyCondition condition = BookCopyCondition.NEW;

    @NotNull(message = "Copy status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    @ToString.Include
    private BookCopyStatus status = BookCopyStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @OneToMany(mappedBy = "bookCopy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Loan> loans = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopy)) return false;
        BookCopy other = (BookCopy) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
