package az.library.library.service.impl;

import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.request.UpdateBookRequest;
import az.library.library.dto.response.BookDetailedResponse;
import az.library.library.dto.response.BookSummaryResponse;
import az.library.library.entity.Author;
import az.library.library.entity.Book;
import az.library.library.entity.Category;
import az.library.library.entity.Publisher;
import az.library.library.enums.BookStatus;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.BookMapper;
import az.library.library.repository.*;
import az.library.library.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookDetailedResponse create(CreateBookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn()))
            throw new IllegalArgumentException("Book with ISBN " + request.getIsbn() + " already exists");
        Book book = bookMapper.toEntityForCreate(request);
        if (request.getPublisherId() != null)
            book.setPublisher(publisherRepository.findById(request.getPublisherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher", request.getPublisherId())));
        java.util.Set<Author> authors = new java.util.HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
        if (authors.size() != request.getAuthorIds().size())
            throw new IllegalArgumentException("Some authors not found");
        book.setAuthors(authors);
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            java.util.Set<Category> cats = new java.util.HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
            book.setCategories(cats);
        }
        return bookMapper.toDetailedResponse(bookRepository.save(book));
    }

    @Override
    public BookDetailedResponse findById(Long id) {
        return bookMapper.toDetailedResponse(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id)));
    }

    @Override
    public List<BookSummaryResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toSummaryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDetailedResponse update(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));
        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getIsbn() != null) {
            if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn()))
                throw new IllegalArgumentException("ISBN " + request.getIsbn() + " already exists");
            book.setIsbn(request.getIsbn());
        }
        if (request.getPublicationYear() != null) book.setPublicationYear(request.getPublicationYear());
        if (request.getEdition() != null) book.setEdition(request.getEdition());
        if (request.getPageCount() != null) book.setPageCount(request.getPageCount());
        if (request.getLanguage() != null) book.setLanguage(request.getLanguage());
        if (request.getSummary() != null) book.setSummary(request.getSummary());
        if (request.getPublisherId() != null) {
            if (request.getPublisherId() == 0) book.setPublisher(null);
            else book.setPublisher(publisherRepository.findById(request.getPublisherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher", request.getPublisherId())));
        }
        if (request.getAuthorIds() != null) {
            java.util.Set<Author> authors = new java.util.HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
            book.setAuthors(authors);
        }
        if (request.getCategoryIds() != null) {
            if (request.getCategoryIds().isEmpty()) book.setCategories(new java.util.HashSet<>());
            else book.setCategories(new java.util.HashSet<>(categoryRepository.findAllById(request.getCategoryIds())));
        }
        return bookMapper.toDetailedResponse(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookRepository.delete(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id)));
    }
}
