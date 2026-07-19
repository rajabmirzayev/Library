package az.library.library.service.impl;

import az.library.library.dto.request.CreatePublisherRequest;
import az.library.library.dto.request.UpdatePublisherRequest;
import az.library.library.dto.response.PublisherDetailedResponse;
import az.library.library.dto.response.PublisherSummaryResponse;
import az.library.library.entity.Publisher;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.PublisherMapper;
import az.library.library.repository.PublisherRepository;
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
class PublisherServiceImplTest {

    @Mock
    private PublisherRepository repo;

    @Mock
    private PublisherMapper mapper;

    @InjectMocks
    private PublisherServiceImpl service;

    @Test
    void Given_ValidRequest_When_Create_Then_ReturnsDetailedResponse() {
        CreatePublisherRequest request = new CreatePublisherRequest();
        request.setName("Qanun Nəşriyyatı");

        Publisher entity = Publisher.builder().name("Qanun Nəşriyyatı").build();
        PublisherDetailedResponse response = new PublisherDetailedResponse();
        response.setId(1L);
        response.setName("Qanun Nəşriyyatı");

        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        PublisherDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Qanun Nəşriyyatı");
        verify(repo).save(entity);
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        Publisher entity = Publisher.builder().id(id).name("Qanun").build();
        PublisherDetailedResponse response = new PublisherDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        PublisherDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Publisher");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Publisher entity = Publisher.builder().id(1L).name("Qanun").build();
        PublisherSummaryResponse summary = new PublisherSummaryResponse();
        summary.setId(1L);
        Page<Publisher> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(repo.findAll(pageable)).willReturn(page);
        given(mapper.toSummaryResponse(entity)).willReturn(summary);

        Page<PublisherSummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void Given_ValidRequest_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdatePublisherRequest request = new UpdatePublisherRequest();
        request.setName("Yeni Ad");

        Publisher entity = Publisher.builder().id(id).name("Qanun").build();
        PublisherDetailedResponse response = new PublisherDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(Publisher.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        PublisherDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        verify(mapper).updateEntity(request, entity);
        verify(repo).save(entity);
    }

    @Test
    void Given_NonExistingId_When_Update_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        UpdatePublisherRequest request = new UpdatePublisherRequest();
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Publisher");
    }

    @Test
    void Given_ExistingId_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        Publisher entity = Publisher.builder().id(id).build();
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
                .hasMessageContaining("Publisher");
    }
}
