package az.library.library.entity;

import az.library.library.enums.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "books", indexes = {
        @Index(name = "idx_book_isbn", columnList = "isbn", unique = true),
        @Index(name = "idx_book_title", columnList = "title"),
        @Index(name = "idx_book_status", columnList = "status")
})
public class Book extends BaseEntity {

    @NotBlank(message = "Book title is required")
    @Size(max = 300, message = "Title must not exceed 300 characters")
    @Column(nullable = false, length = 300)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 17, message = "ISBN must be between 10 and 17 characters")
    @Column(nullable = false, unique = true, length = 17)
    private String isbn;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Size(max = 50, message = "Edition must not exceed 50 characters")
    @Column(length = 50)
    private String edition;

    @Column(name = "page_count")
    private Integer pageCount;

    @Size(max = 50, message = "Language must not exceed 50 characters")
    @Column(length = 50)
    private String language;

    @Size(max = 5000, message = "Summary must not exceed 5000 characters")
    @Column(length = 5000)
    private String summary;

    @NotNull(message = "Book status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private BookStatus status = BookStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Builder.Default
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BookCopy> copies = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
}
