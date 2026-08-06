package az.library.library.repository;

import az.library.library.entity.Fine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    List<Fine> findByMemberId(Long memberId);

    List<Fine> findByLoanId(Long loanId);

    @Override
    @EntityGraph(attributePaths = {"loan", "member"})
    Optional<Fine> findById(Long id);

    @Override
    @EntityGraph(attributePaths = "member")
    Page<Fine> findAll(Pageable pageable);

}
