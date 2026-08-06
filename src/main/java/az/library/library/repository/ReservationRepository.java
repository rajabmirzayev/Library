package az.library.library.repository;

import az.library.library.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByMemberId(Long memberId);

    List<Reservation> findByBookId(Long bookId);

    @Override
    @EntityGraph(attributePaths = {"book", "member"})
    Optional<Reservation> findById(Long id);

    @Override
    @EntityGraph(attributePaths = "book")
    Page<Reservation> findAll(Pageable pageable);

}
