package az.library.library.repository;

import az.library.library.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    List<Fine> findByMemberId(Long memberId);

    List<Fine> findByLoanId(Long loanId);

}
