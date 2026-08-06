package az.library.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "publishers", indexes = {
        @Index(name = "idx_publisher_name", columnList = "name")
})
public class Publisher extends BaseEntity {

    @NotBlank(message = "Publisher name is required")
    @Size(max = 200, message = "Publisher name must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    @ToString.Include
    private String name;

    @Size(max = 500, message = "Address must not exceed 500 characters")
    @Column(length = 500)
    @ToString.Include
    private String address;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    @Column(length = 20)
    @ToString.Include
    private String phone;

    @Email(message = "Invalid email format")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    @Column(length = 150)
    @ToString.Include
    private String email;

    @Size(max = 200, message = "Website must not exceed 200 characters")
    @Column(length = 200)
    @ToString.Include
    private String website;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publisher)) return false;
        Publisher other = (Publisher) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
