package az.library.library.service.impl;

import az.library.library.mapper.BookMapper;
import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.response.CreateBookResponse;
import az.library.library.entity.Author;
import az.library.library.entity.Book;
import az.library.library.entity.Category;
import az.library.library.entity.Publisher;
import az.library.library.repository.AuthorRepository;
import az.library.library.repository.BookRepository;
import az.library.library.repository.CategoryRepository;
import az.library.library.repository.PublisherRepository;
import az.library.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Transactional
    public CreateBookResponse createBook(CreateBookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + request.getIsbn() + " already exists");
        }

        Book book = bookMapper.toEntity(request);

        if (request.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(request.getPublisherId())
                    .orElseThrow(() -> new IllegalArgumentException("Publisher not found with id: " + request.getPublisherId()));
            book.setPublisher(publisher);
        }

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));
        if (authors.size() != request.getAuthorIds().size()) {
            throw new IllegalArgumentException("One or more authors not found");
        }
        book.setAuthors(authors);

        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
            if (categories.size() != request.getCategoryIds().size()) {
                throw new IllegalArgumentException("One or more categories not found");
            }
            book.setCategories(categories);
        }

        Book savedBook = bookRepository.save(book);
        Book detailedBook = bookRepository.findByIdWithDetails(savedBook.getId())
                .orElse(savedBook);

        return bookMapper.toResponse(detailedBook);
    }

}
