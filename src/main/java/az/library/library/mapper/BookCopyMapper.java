package az.library.library.mapper;

import az.library.library.dto.request.CreateBookCopyRequest;
import az.library.library.dto.request.UpdateBookCopyRequest;
import az.library.library.dto.response.BookCopyDetailedResponse;
import az.library.library.dto.response.BookCopySummaryResponse;
import az.library.library.entity.Book;
import az.library.library.entity.BookCopy;
import az.library.library.enums.BookCopyCondition;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "condition", source = "condition", qualifiedByName = "mapCondition")
    @Mapping(target = "book", source = "bookId", qualifiedByName = "refBook")
    BookCopy toEntityForCreate(CreateBookCopyRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "condition", source = "condition", qualifiedByName = "mapCondition")
    @Mapping(target = "book", source = "bookId", qualifiedByName = "refBook")
    void updateEntity(UpdateBookCopyRequest request, @MappingTarget BookCopy bookCopy);

    @Mapping(target = "bookId", expression = "java(bc.getBook() != null ? bc.getBook().getId() : null)")
    @Mapping(target = "bookTitle", expression = "java(bc.getBook() != null ? bc.getBook().getTitle() : null)")
    @Mapping(target = "bookIsbn", expression = "java(bc.getBook() != null ? bc.getBook().getIsbn() : null)")
    BookCopyDetailedResponse toDetailedResponse(BookCopy bc);

    BookCopySummaryResponse toSummaryResponse(BookCopy bc);

    @Named("mapCondition")
    default BookCopyCondition mapCondition(String c) {
        return c == null ? BookCopyCondition.NEW : BookCopyCondition.valueOf(c.toUpperCase());
    }

    @Named("refBook")
    default Book refBook(Long id) {
        if (id == null) return null;
        Book b = new Book();
        b.setId(id);
        return b;
    }

}
