package az.library.library.repository;

import az.library.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    boolean existsByIsbn(String isbn);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.publisher LEFT JOIN FETCH b.authors LEFT JOIN FETCH b.categories WHERE b.id = :id AND b.deleted = false")
    Optional<Book> findByIdWithDetails(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {"publisher", "authors"})
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"publisher", "authors"})
    Page<Book> findAll(Specification<Book> spec, Pageable pageable);

}
