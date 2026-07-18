package az.library.library.mapper;

import az.library.library.dto.request.CreatePublisherRequest;
import az.library.library.dto.request.UpdatePublisherRequest;
import az.library.library.dto.response.PublisherDetailedResponse;
import az.library.library.dto.response.PublisherSummaryResponse;
import az.library.library.entity.Publisher;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "books", ignore = true)
    Publisher toEntityForCreate(CreatePublisherRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updateEntity(UpdatePublisherRequest request, @MappingTarget Publisher publisher);

    @Mapping(target = "bookCount", expression = "java(publisher.getBooks() != null ? publisher.getBooks().size() : 0)")
    PublisherDetailedResponse toDetailedResponse(Publisher publisher);

    PublisherSummaryResponse toSummaryResponse(Publisher publisher);

}
