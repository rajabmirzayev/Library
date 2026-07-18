package az.library.library.mapper;

import az.library.library.dto.request.CreateAuthorRequest;
import az.library.library.dto.request.UpdateAuthorRequest;
import az.library.library.dto.response.AuthorDetailedResponse;
import az.library.library.dto.response.AuthorSummaryResponse;
import az.library.library.entity.Author;

import java.util.stream.Collectors;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toEntityForCreate(CreateAuthorRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updateEntity(UpdateAuthorRequest request, @MappingTarget Author author);

    @Mapping(target = "bookTitles", expression = "java(author.getBooks().stream().map(b -> b.getTitle()).collect(java.util.stream.Collectors.toSet()))")
    AuthorDetailedResponse toDetailedResponse(Author author);

    AuthorSummaryResponse toSummaryResponse(Author author);

}
