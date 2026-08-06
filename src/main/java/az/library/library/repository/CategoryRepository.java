package az.library.library.repository;

import az.library.library.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @EntityGraph(attributePaths = {"parent", "subcategories"})
    Optional<Category> findById(Long id);

}
