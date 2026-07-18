package az.library.library.mapper;

import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.request.UpdateBookRequest;
import az.library.library.dto.response.BookDetailedResponse;
import az.library.library.dto.response.BookSummaryResponse;
import az.library.library.entity.Author;
import az.library.library.entity.Book;
import az.library.library.entity.Category;
import az.library.library.entity.Publisher;

import java.util.stream.Collectors;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "copies", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "publisher", source = "publisherId", qualifiedByName = "refPublisher")
    @Mapping(target = "authors", source = "authorIds", qualifiedByName = "refAuthors")
    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "refCategories")
    Book toEntityForCreate(CreateBookRequest request);

    @Mapping(target = "publisherId", expression = "java(book.getPublisher() != null ? book.getPublisher().getId() : null)")
    @Mapping(target = "publisherName", expression = "java(book.getPublisher() != null ? book.getPublisher().getName() : null)")
    @Mapping(target = "authorNames", expression = "java(nameSet(book.getAuthors()))")
    @Mapping(target = "categoryNames", expression = "java(categoryNameSet(book.getCategories()))")
    BookDetailedResponse toDetailedResponse(Book book);

    @Mapping(target = "publisherName", expression = "java(book.getPublisher() != null ? book.getPublisher().getName() : null)")
    @Mapping(target = "authorNames", expression = "java(flatNames(book.getAuthors()))")
    BookSummaryResponse toSummaryResponse(Book book);

    @Named("refPublisher")
    default Publisher refPublisher(Long id) {
        if (id == null) return null;
        Publisher p = new Publisher();
        p.setId(id);
        return p;
    }

    @Named("refAuthors")
    default java.util.Set<Author> refAuthors(java.util.Set<Long> ids) {
        if (ids == null) return java.util.Set.of();
        return ids.stream().map(id -> {
            Author a = new Author();
            a.setId(id);
            return a;
        }).collect(Collectors.toSet());
    }

    @Named("refCategories")
    default java.util.Set<Category> refCategories(java.util.Set<Long> ids) {
        if (ids == null) return java.util.Set.of();
        return ids.stream().map(id -> {
            Category c = new Category();
            c.setId(id);
            return c;
        }).collect(Collectors.toSet());
    }

    default java.util.Set<String> nameSet(java.util.Set<Author> authors) {
        if (authors == null) return java.util.Set.of();
        return authors.stream().map(a -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.toSet());
    }

    default java.util.Set<String> categoryNameSet(java.util.Set<Category> categories) {
        if (categories == null) return java.util.Set.of();
        return categories.stream().map(Category::getName).collect(Collectors.toSet());
    }

    default String flatNames(java.util.Set<Author> authors) {
        if (authors == null || authors.isEmpty()) return "";
        return authors.stream().map(a -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.joining(", "));
    }

}
