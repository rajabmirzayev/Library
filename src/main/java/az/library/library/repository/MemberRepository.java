package az.library.library.repository;

import az.library.library.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    boolean existsByMembershipNumber(String membershipNumber);

    @Override
    @EntityGraph(attributePaths = {"loans", "fines"})
    Optional<Member> findById(Long id);

}
