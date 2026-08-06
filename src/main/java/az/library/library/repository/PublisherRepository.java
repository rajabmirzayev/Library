package az.library.library.repository;

import az.library.library.entity.Publisher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Override
    @EntityGraph(attributePaths = "books")
    Optional<Publisher> findById(Long id);

}
