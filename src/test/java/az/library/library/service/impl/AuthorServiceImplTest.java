package az.library.library.service.impl;

import az.library.library.dto.request.CreateAuthorRequest;
import az.library.library.dto.request.UpdateAuthorRequest;
import az.library.library.dto.response.AuthorDetailedResponse;
import az.library.library.dto.response.AuthorSummaryResponse;
import az.library.library.entity.Author;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.AuthorMapper;
import az.library.library.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository repo;

    @Mock
    private AuthorMapper mapper;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    void Given_ValidRequest_When_Create_Then_ReturnsDetailedResponse() {
        CreateAuthorRequest request = new CreateAuthorRequest();
        request.setFirstName("Çingiz");
        request.setLastName("Abdullayev");

        Author entity = Author.builder().firstName("Çingiz").lastName("Abdullayev").build();
        AuthorDetailedResponse response = new AuthorDetailedResponse();
        response.setId(1L);
        response.setFirstName("Çingiz");

        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        AuthorDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Çingiz");
        verify(mapper).toEntityForCreate(request);
        verify(repo).save(entity);
        verify(mapper).toDetailedResponse(entity);
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        Author entity = Author.builder().id(id).firstName("Çingiz").build();
        AuthorDetailedResponse response = new AuthorDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        AuthorDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Author")
                .hasMessageContaining("999");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Author entity = Author.builder().id(1L).firstName("Çingiz").build();
        AuthorSummaryResponse summary = new AuthorSummaryResponse();
        summary.setId(1L);
        Page<Author> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(repo.findAll(pageable)).willReturn(page);
        given(mapper.toSummaryResponse(entity)).willReturn(summary);

        Page<AuthorSummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
    }

    @Test
    void Given_ValidRequest_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdateAuthorRequest request = new UpdateAuthorRequest();
        request.setFirstName("Yenilənmiş");

        Author entity = Author.builder().id(id).firstName("Çingiz").build();
        AuthorDetailedResponse response = new AuthorDetailedResponse();
        response.setId(id);
        response.setFirstName("Yenilənmiş");

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(Author.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        AuthorDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        verify(mapper).updateEntity(request, entity);
        verify(repo).save(entity);
    }

    @Test
    void Given_NonExistingId_When_Update_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        UpdateAuthorRequest request = new UpdateAuthorRequest();
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Author");
    }

    @Test
    void Given_ExistingId_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        Author entity = Author.builder().id(id).build();
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
                .hasMessageContaining("Author");
    }
}
