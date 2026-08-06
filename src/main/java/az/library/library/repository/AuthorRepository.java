package az.library.library.repository;

import az.library.library.entity.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Override
    @EntityGraph(attributePaths = "books")
    Optional<Author> findById(Long id);

}
