package az.library.library.repository;

import az.library.library.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    boolean existsByBarcode(String barcode);

    Optional<BookCopy> findByBarcode(String barcode);

    @Override
    @Query("SELECT bc FROM BookCopy bc JOIN FETCH bc.book WHERE bc.id = :id")
    Optional<BookCopy> findById(@Param("id") Long id);

}
