package az.library.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "authors", indexes = {
        @Index(name = "idx_author_name", columnList = "first_name, last_name")
})
public class Author extends BaseEntity {

    @NotBlank(message = "Author first name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Column(name = "first_name", nullable = false, length = 100)
    @ToString.Include
    private String firstName;

    @NotBlank(message = "Author last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Column(name = "last_name", nullable = false, length = 100)
    @ToString.Include
    private String lastName;

    @Size(max = 2000, message = "Biography must not exceed 2000 characters")
    @Column(length = 2000)
    @ToString.Include
    private String biography;

    @Column(name = "birth_date")
    @ToString.Include
    private LocalDate birthDate;

    @Size(max = 100, message = "Nationality must not exceed 100 characters")
    @Column(length = 100)
    @ToString.Include
    private String nationality;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Book> books = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author other = (Author) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
