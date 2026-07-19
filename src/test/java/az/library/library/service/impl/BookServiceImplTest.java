package az.library.library.service.impl;

import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.request.UpdateBookRequest;
import az.library.library.dto.response.BookDetailedResponse;
import az.library.library.dto.response.BookSummaryResponse;
import az.library.library.entity.Author;
import az.library.library.entity.Book;
import az.library.library.entity.Category;
import az.library.library.entity.Publisher;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.BookMapper;
import az.library.library.repository.*;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    void Given_ValidRequest_When_Create_Then_ReturnsDetailedResponse() {
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Üçippyşq");
        request.setIsbn("978-0-13-468599-1");
        request.setAuthorIds(Set.of(1L));

        Author author = Author.builder().id(1L).firstName("Çingiz").build();
        Book entity = Book.builder().title("Üçippyşq").isbn("978-0-13-468599-1").build();
        BookDetailedResponse response = new BookDetailedResponse();
        response.setId(1L);

        given(bookRepository.existsByIsbn("978-0-13-468599-1")).willReturn(false);
        given(bookMapper.toEntityForCreate(request)).willReturn(entity);
        given(authorRepository.findAllById(Set.of(1L))).willReturn(List.of(author));
        given(bookRepository.save(entity)).willReturn(entity);
        given(bookMapper.toDetailedResponse(entity)).willReturn(response);

        BookDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(bookRepository).save(entity);
    }

    @Test
    void Given_DuplicateIsbn_When_Create_Then_ThrowsIllegalArgumentException() {
        CreateBookRequest request = new CreateBookRequest();
        request.setIsbn("978-0-13-468599-1");

        given(bookRepository.existsByIsbn("978-0-13-468599-1")).willReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void Given_SomeAuthorsNotFound_When_Create_Then_ThrowsIllegalArgumentException() {
        CreateBookRequest request = new CreateBookRequest();
        request.setIsbn("978-0-13-468599-1");
        request.setAuthorIds(Set.of(1L, 2L));

        Author author1 = Author.builder().id(1L).build();
        given(bookRepository.existsByIsbn(any())).willReturn(false);
        given(bookMapper.toEntityForCreate(request)).willReturn(new Book());
        given(authorRepository.findAllById(Set.of(1L, 2L))).willReturn(List.of(author1));

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Some authors not found");
    }

    @Test
    void Given_PublisherId_When_Create_Then_SetsPublisher() {
        CreateBookRequest request = new CreateBookRequest();
        request.setIsbn("978-0-13-468599-1");
        request.setPublisherId(5L);
        request.setAuthorIds(Set.of(1L));

        Publisher publisher = Publisher.builder().id(5L).name("Qanun").build();
        Author author = Author.builder().id(1L).build();
        Book entity = Book.builder().build();
        BookDetailedResponse response = new BookDetailedResponse();

        given(bookRepository.existsByIsbn(any())).willReturn(false);
        given(bookMapper.toEntityForCreate(request)).willReturn(entity);
        given(publisherRepository.findById(5L)).willReturn(Optional.of(publisher));
        given(authorRepository.findAllById(any())).willReturn(List.of(author));
        given(bookRepository.save(entity)).willReturn(entity);
        given(bookMapper.toDetailedResponse(entity)).willReturn(response);

        BookDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        assertThat(captor.getValue().getPublisher()).isEqualTo(publisher);
    }

    @Test
    void Given_NonExistingPublisherId_When_Create_Then_ThrowsResourceNotFoundException() {
        CreateBookRequest request = new CreateBookRequest();
        request.setIsbn("978-0-13-468599-1");
        request.setPublisherId(999L);
        request.setAuthorIds(Set.of(1L));

        given(bookRepository.existsByIsbn(any())).willReturn(false);
        given(bookMapper.toEntityForCreate(request)).willReturn(new Book());
        given(publisherRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Publisher");
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        Book entity = Book.builder().id(id).title("Test").build();
        BookDetailedResponse response = new BookDetailedResponse();
        response.setId(id);

        given(bookRepository.findById(id)).willReturn(Optional.of(entity));
        given(bookMapper.toDetailedResponse(entity)).willReturn(response);

        BookDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(bookRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Book entity = Book.builder().id(1L).title("Test").build();
        BookSummaryResponse summary = new BookSummaryResponse();
        summary.setId(1L);
        Page<Book> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(bookRepository.findAll(pageable)).willReturn(page);
        given(bookMapper.toSummaryResponse(entity)).willReturn(summary);

        Page<BookSummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void Given_ValidRequest_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdateBookRequest request = new UpdateBookRequest();
        request.setTitle("Yeni Başlıq");

        Book entity = Book.builder().id(id).title("Köhnə").isbn("978-0-13-468599-1").build();
        BookDetailedResponse response = new BookDetailedResponse();
        response.setId(id);

        given(bookRepository.findById(id)).willReturn(Optional.of(entity));
        given(bookRepository.save(any(Book.class))).willReturn(entity);
        given(bookMapper.toDetailedResponse(entity)).willReturn(response);

        BookDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        verify(bookRepository).save(entity);
    }

    @Test
    void Given_DuplicateIsbn_When_Update_Then_ThrowsIllegalArgumentException() {
        Long id = 1L;
        UpdateBookRequest request = new UpdateBookRequest();
        request.setIsbn("978-0-00-000000-1");

        Book entity = Book.builder().id(id).isbn("978-0-13-468599-1").build();
        given(bookRepository.findById(id)).willReturn(Optional.of(entity));
        given(bookRepository.existsByIsbn("978-0-00-000000-1")).willReturn(true);

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void Given_SameIsbn_When_Update_Then_UpdatesSuccessfully() {
        Long id = 1L;
        UpdateBookRequest request = new UpdateBookRequest();
        request.setIsbn("978-0-13-468599-1");

        Book entity = Book.builder().id(id).isbn("978-0-13-468599-1").build();
        BookDetailedResponse response = new BookDetailedResponse();
        response.setId(id);

        given(bookRepository.findById(id)).willReturn(Optional.of(entity));
        given(bookRepository.save(any(Book.class))).willReturn(entity);
        given(bookMapper.toDetailedResponse(entity)).willReturn(response);

        BookDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        verify(bookRepository, never()).existsByIsbn(any());
    }

    @Test
    void Given_PublisherIdZero_When_Update_Then_UnsetsPublisher() {
        Long id = 1L;
        UpdateBookRequest request = new UpdateBookRequest();
        request.setPublisherId(0L);

        Publisher publisher = Publisher.builder().id(5L).build();
        Book entity = Book.builder().id(id).publisher(publisher).build();
        BookDetailedResponse response = new BookDetailedResponse();

        given(bookRepository.findById(id)).willReturn(Optional.of(entity));
        given(bookRepository.save(any(Book.class))).willReturn(entity);
        given(bookMapper.toDetailedResponse(entity)).willReturn(response);

        BookDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        assertThat(captor.getValue().getPublisher()).isNull();
    }

    @Test
    void Given_NonExistingId_When_Update_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        UpdateBookRequest request = new UpdateBookRequest();
        given(bookRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book");
    }

    @Test
    void Given_EmptyCategoryIds_When_Update_Then_ClearsCategories() {
        Long id = 1L;
        UpdateBookRequest request = new UpdateBookRequest();
        request.setCategoryIds(new HashSet<>());

        Category cat = Category.builder().id(1L).build();
        Book entity = Book.builder().id(id).categories(new HashSet<>(Set.of(cat))).build();
        BookDetailedResponse response = new BookDetailedResponse();

        given(bookRepository.findById(id)).willReturn(Optional.of(entity));
        given(bookRepository.save(any(Book.class))).willReturn(entity);
        given(bookMapper.toDetailedResponse(entity)).willReturn(response);

        BookDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        assertThat(captor.getValue().getCategories()).isEmpty();
    }

    @Test
    void Given_ExistingId_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        Book entity = Book.builder().id(id).build();
        given(bookRepository.findById(id)).willReturn(Optional.of(entity));

        service.delete(id);

        verify(bookRepository).delete(entity);
    }

    @Test
    void Given_NonExistingId_When_Delete_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(bookRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book");
    }
}
