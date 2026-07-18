package az.library.library.mapper;

import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.response.CreateBookResponse;
import az.library.library.entity.Author;
import az.library.library.entity.Book;
import az.library.library.entity.Category;
import az.library.library.entity.Publisher;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "copies", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "publisher", source = "publisherId", qualifiedByName = "mapPublisher")
    @Mapping(target = "authors", source = "authorIds", qualifiedByName = "mapAuthors")
    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "mapCategories")
    Book toEntity(CreateBookRequest request);

    @Mapping(target = "publisherName", expression = "java(book.getPublisher() != null ? book.getPublisher().getName() : null)")
    @Mapping(target = "authorNames", expression = "java(mapAuthorNames(book.getAuthors()))")
    @Mapping(target = "categoryNames", expression = "java(mapCategoryNames(book.getCategories()))")
    CreateBookResponse toResponse(Book book);

    @Named("mapPublisher")
    default Publisher mapPublisher(Long publisherId) {
        if (publisherId == null) return null;
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
        return publisher;
    }

    @Named("mapAuthors")
    default Set<Author> mapAuthors(Set<Long> authorIds) {
        if (authorIds == null) return Set.of();
        return authorIds.stream()
                .map(id -> {
                    Author author = new Author();
                    author.setId(id);
                    return author;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapCategories")
    default Set<Category> mapCategories(Set<Long> categoryIds) {
        if (categoryIds == null) return Set.of();
        return categoryIds.stream()
                .map(id -> {
                    Category category = new Category();
                    category.setId(id);
                    return category;
                })
                .collect(Collectors.toSet());
    }

    default Set<String> mapAuthorNames(Set<Author> authors) {
        if (authors == null) return Set.of();
        return authors.stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .collect(Collectors.toSet());
    }

    default Set<String> mapCategoryNames(Set<Category> categories) {
        if (categories == null) return Set.of();
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }
}
