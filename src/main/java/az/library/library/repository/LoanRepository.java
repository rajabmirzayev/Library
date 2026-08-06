package az.library.library.repository;

import az.library.library.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByMemberId(Long memberId);

    List<Loan> findByBookCopyId(Long bookCopyId);

    @Override
    @Query("SELECT l FROM Loan l LEFT JOIN FETCH l.bookCopy bc LEFT JOIN FETCH bc.book LEFT JOIN FETCH l.member WHERE l.id = :id")
    Optional<Loan> findById(@Param("id") Long id);

    @Override
    @Query(value = "SELECT DISTINCT l FROM Loan l LEFT JOIN FETCH l.bookCopy bc LEFT JOIN FETCH bc.book LEFT JOIN FETCH l.member",
           countQuery = "SELECT COUNT(l) FROM Loan l")
    Page<Loan> findAll(Pageable pageable);

}
