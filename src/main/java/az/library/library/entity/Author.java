package az.library.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "authors", indexes = {
        @Index(name = "idx_author_name", columnList = "first_name, last_name")
})
public class Author extends BaseEntity {

    @NotBlank(message = "Author first name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Author last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Size(max = 2000, message = "Biography must not exceed 2000 characters")
    @Column(length = 2000)
    private String biography;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 100, message = "Nationality must not exceed 100 characters")
    @Column(length = 100)
    private String nationality;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Book> books = new ArrayList<>();

}
