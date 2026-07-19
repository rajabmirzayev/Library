package az.library.library.service.impl;

import az.library.library.dto.request.CreateBookCopyRequest;
import az.library.library.dto.request.UpdateBookCopyRequest;
import az.library.library.dto.response.BookCopyDetailedResponse;
import az.library.library.dto.response.BookCopySummaryResponse;
import az.library.library.entity.Book;
import az.library.library.entity.BookCopy;
import az.library.library.enums.BookCopyCondition;
import az.library.library.enums.BookCopyStatus;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.BookCopyMapper;
import az.library.library.repository.BookCopyRepository;
import az.library.library.repository.BookRepository;
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
class BookCopyServiceImplTest {

    @Mock
    private BookCopyRepository repo;
    @Mock
    private BookRepository bookRepo;
    @Mock
    private BookCopyMapper mapper;

    @InjectMocks
    private BookCopyServiceImpl service;

    @Test
    void Given_ValidRequest_When_Create_Then_ReturnsDetailedResponse() {
        CreateBookCopyRequest request = new CreateBookCopyRequest();
        request.setBarcode("BK-2026-001");
        request.setBookId(1L);

        Book book = Book.builder().id(1L).build();
        BookCopy entity = BookCopy.builder().barcode("BK-2026-001").build();
        BookCopyDetailedResponse response = new BookCopyDetailedResponse();
        response.setId(1L);

        given(repo.existsByBarcode("BK-2026-001")).willReturn(false);
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(bookRepo.findById(1L)).willReturn(Optional.of(book));
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        BookCopyDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        ArgumentCaptor<BookCopy> captor = ArgumentCaptor.forClass(BookCopy.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getBook()).isEqualTo(book);
        assertThat(captor.getValue().getStatus()).isEqualTo(BookCopyStatus.AVAILABLE);
    }

    @Test
    void Given_DuplicateBarcode_When_Create_Then_ThrowsIllegalArgumentException() {
        CreateBookCopyRequest request = new CreateBookCopyRequest();
        request.setBarcode("BK-2026-001");

        given(repo.existsByBarcode("BK-2026-001")).willReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void Given_NonExistingBookId_When_Create_Then_ThrowsResourceNotFoundException() {
        CreateBookCopyRequest request = new CreateBookCopyRequest();
        request.setBarcode("BK-2026-001");
        request.setBookId(999L);

        BookCopy entity = BookCopy.builder().build();
        given(repo.existsByBarcode("BK-2026-001")).willReturn(false);
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(bookRepo.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book");
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        BookCopy entity = BookCopy.builder().id(id).barcode("BK-001").build();
        BookCopyDetailedResponse response = new BookCopyDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        BookCopyDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("BookCopy");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        BookCopy entity = BookCopy.builder().id(1L).barcode("BK-001").build();
        BookCopySummaryResponse summary = new BookCopySummaryResponse();
        summary.setId(1L);
        Page<BookCopy> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(repo.findAll(pageable)).willReturn(page);
        given(mapper.toSummaryResponse(entity)).willReturn(summary);

        Page<BookCopySummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void Given_ValidRequest_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdateBookCopyRequest request = new UpdateBookCopyRequest();
        request.setShelfLocation("B-1-05");

        BookCopy entity = BookCopy.builder().id(id).barcode("BK-001").build();
        BookCopyDetailedResponse response = new BookCopyDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(BookCopy.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        BookCopyDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        ArgumentCaptor<BookCopy> captor = ArgumentCaptor.forClass(BookCopy.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getShelfLocation()).isEqualTo("B-1-05");
    }

    @Test
    void Given_DuplicateBarcode_When_Update_Then_ThrowsIllegalArgumentException() {
        Long id = 1L;
        UpdateBookCopyRequest request = new UpdateBookCopyRequest();
        request.setBarcode("BK-NEW");

        BookCopy entity = BookCopy.builder().id(id).barcode("BK-001").build();
        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.existsByBarcode("BK-NEW")).willReturn(true);

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void Given_SameBarcode_When_Update_Then_UpdatesSuccessfully() {
        Long id = 1L;
        UpdateBookCopyRequest request = new UpdateBookCopyRequest();
        request.setBarcode("BK-001");

        BookCopy entity = BookCopy.builder().id(id).barcode("BK-001").build();
        BookCopyDetailedResponse response = new BookCopyDetailedResponse();

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(BookCopy.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        BookCopyDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        verify(repo, never()).existsByBarcode(any());
    }

    @Test
    void Given_Condition_When_Update_Then_SetsCondition() {
        Long id = 1L;
        UpdateBookCopyRequest request = new UpdateBookCopyRequest();
        request.setCondition("GOOD");

        BookCopy entity = BookCopy.builder().id(id).barcode("BK-001").build();
        BookCopyDetailedResponse response = new BookCopyDetailedResponse();

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(BookCopy.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        service.update(id, request);

        ArgumentCaptor<BookCopy> captor = ArgumentCaptor.forClass(BookCopy.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getCondition()).isEqualTo(BookCopyCondition.GOOD);
    }

    @Test
    void Given_ExistingId_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        BookCopy entity = BookCopy.builder().id(id).build();
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
                .hasMessageContaining("BookCopy");
    }
}
