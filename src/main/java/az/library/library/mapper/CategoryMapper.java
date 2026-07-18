package az.library.library.mapper;

import az.library.library.dto.request.CreateCategoryRequest;
import az.library.library.dto.request.UpdateCategoryRequest;
import az.library.library.dto.response.CategoryDetailedResponse;
import az.library.library.dto.response.CategorySummaryResponse;
import az.library.library.entity.Category;

import java.util.stream.Collectors;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "parent", source = "parentId")
    @Mapping(target = "subcategories", ignore = true)
    @Mapping(target = "books", ignore = true)
    Category toEntityForCreate(CreateCategoryRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "subcategories", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "parent", source = "parentId")
    void updateEntity(UpdateCategoryRequest request, @MappingTarget Category category);

    @Mapping(target = "parentId", expression = "java(category.getParent() != null ? category.getParent().getId() : null)")
    @Mapping(target = "parentName", expression = "java(category.getParent() != null ? category.getParent().getName() : null)")
    @Mapping(target = "subcategories", expression = "java(category.getSubcategories().stream().map(this::toSummary).collect(java.util.stream.Collectors.toList()))")
    CategoryDetailedResponse toDetailedResponse(Category category);

    default CategorySummaryResponse toSummary(Category category) {
        if (category == null) return null;
        return new CategorySummaryResponse(category.getId(), category.getName());
    }

    CategorySummaryResponse toSummaryResponse(Category category);

    default Category mapParent(Long parentId) {
        if (parentId == null) return null;
        Category c = new Category();
        c.setId(parentId);
        return c;
    }

}
