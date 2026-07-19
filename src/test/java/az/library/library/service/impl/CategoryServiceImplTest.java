package az.library.library.service.impl;

import az.library.library.dto.request.CreateCategoryRequest;
import az.library.library.dto.request.UpdateCategoryRequest;
import az.library.library.dto.response.CategoryDetailedResponse;
import az.library.library.dto.response.CategorySummaryResponse;
import az.library.library.entity.Category;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.CategoryMapper;
import az.library.library.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository repo;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryServiceImpl service;

    @Test
    void Given_ValidRequest_When_Create_Then_ReturnsDetailedResponse() {
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Bədii ədəbiyyat");

        Category entity = Category.builder().name("Bədii ədəbiyyat").build();
        CategoryDetailedResponse response = new CategoryDetailedResponse();
        response.setId(1L);

        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        CategoryDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        verify(repo).save(entity);
    }

    @Test
    void Given_ParentId_When_Create_Then_SetsParentCategory() {
        Long parentId = 10L;
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Uşaq kitabları");
        request.setParentId(parentId);

        Category parentEntity = Category.builder().id(parentId).name("Ədəbiyyat").build();
        Category entity = Category.builder().name("Uşaq kitabları").build();
        CategoryDetailedResponse response = new CategoryDetailedResponse();
        response.setId(2L);

        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.findById(parentId)).willReturn(Optional.of(parentEntity));
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        CategoryDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getParent()).isEqualTo(parentEntity);
    }

    @Test
    void Given_NonExistingParentId_When_Create_Then_ThrowsResourceNotFoundException() {
        Long parentId = 999L;
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Test");
        request.setParentId(parentId);

        Category entity = Category.builder().name("Test").build();
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.findById(parentId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category");
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        Category entity = Category.builder().id(id).name("Bədii").build();
        CategoryDetailedResponse response = new CategoryDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        CategoryDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Category entity = Category.builder().id(1L).name("Elmi").build();
        CategorySummaryResponse summary = new CategorySummaryResponse();
        summary.setId(1L);
        Page<Category> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(repo.findAll(pageable)).willReturn(page);
        given(mapper.toSummaryResponse(entity)).willReturn(summary);

        Page<CategorySummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void Given_ValidRequest_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setName("Yenilənmiş");

        Category entity = Category.builder().id(id).name("Köhnə").build();
        CategoryDetailedResponse response = new CategoryDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(Category.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        CategoryDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        verify(mapper).updateEntity(request, entity);
        verify(repo).save(entity);
    }

    @Test
    void Given_NonExistingId_When_Update_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        UpdateCategoryRequest request = new UpdateCategoryRequest();
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category");
    }

    @Test
    void Given_ExistingId_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        Category entity = Category.builder().id(id).build();
        given(repo.findById(id)).willReturn(Optional.of(entity));

        service.delete(id);

        verify(repo).delete(entity);
    }

    @Test
    void Given_NonExistingId_When_Delete_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category");
    }
}
