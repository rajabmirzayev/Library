package az.library.library.repository.specification;

import az.library.library.dto.request.BookSearchCriteria;
import az.library.library.entity.Author;
import az.library.library.entity.Book;
import az.library.library.entity.Category;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class BookSpecification {

    private BookSpecification() {
    }

    public static Specification<Book> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }

    public static Specification<Book> titleContains(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isBlank()) return null;
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Book> hasAuthorId(Long authorId) {
        return (root, query, cb) -> {
            if (authorId == null) return null;
            Join<Book, Author> authors = root.join("authors", JoinType.INNER);
            return cb.equal(authors.get("id"), authorId);
        };
    }

    public static Specification<Book> hasCategoryId(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return null;
            Join<Book, Category> categories = root.join("categories", JoinType.INNER);
            return cb.equal(categories.get("id"), categoryId);
        };
    }

    public static Specification<Book> hasPublisherId(Long publisherId) {
        return (root, query, cb) -> {
            if (publisherId == null) return null;
            return cb.equal(root.get("publisher").get("id"), publisherId);
        };
    }

    public static Specification<Book> isbnEquals(String isbn) {
        return (root, query, cb) -> {
            if (isbn == null || isbn.isBlank()) return null;
            return cb.equal(root.get("isbn"), isbn);
        };
    }

    public static Specification<Book> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            List<Predicate> predicates = new ArrayList<>(2);
            if (min != null) predicates.add(cb.greaterThanOrEqualTo(root.get("price"), min));
            if (max != null) predicates.add(cb.lessThanOrEqualTo(root.get("price"), max));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Book> publicationYearBetween(Integer start, Integer end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            List<Predicate> predicates = new ArrayList<>(2);
            if (start != null) predicates.add(cb.greaterThanOrEqualTo(root.get("publicationYear"), start));
            if (end != null) predicates.add(cb.lessThanOrEqualTo(root.get("publicationYear"), end));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Book> build(BookSearchCriteria criteria) {
        Specification<Book> spec = Specification.where(notDeleted())
                .and(titleContains(criteria.title()))
                .and(hasAuthorId(criteria.authorId()))
                .and(hasCategoryId(criteria.categoryId()))
                .and(hasPublisherId(criteria.publisherId()))
                .and(isbnEquals(criteria.isbn()))
                .and(priceBetween(criteria.minPrice(), criteria.maxPrice()))
                .and(publicationYearBetween(criteria.startYear(), criteria.endYear()));
        return (root, query, cb) -> {
            query.distinct(true);
            return spec.toPredicate(root, query, cb);
        };
    }
}
