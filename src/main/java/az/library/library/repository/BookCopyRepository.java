package az.library.library.repository;

import az.library.library.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    boolean existsByBarcode(String barcode);

    Optional<BookCopy> findByBarcode(String barcode);

}
