package az.library.library.service;

import az.library.library.dto.request.BookSearchCriteria;
import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.request.UpdateBookRequest;
import az.library.library.dto.response.BookDetailedResponse;
import az.library.library.dto.response.BookSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDetailedResponse create(CreateBookRequest request);

    BookDetailedResponse findById(Long id);

    Page<BookSummaryResponse> findAll(Pageable pageable);

    Page<BookSummaryResponse> search(BookSearchCriteria criteria, Pageable pageable);

    BookDetailedResponse update(Long id, UpdateBookRequest request);

    void delete(Long id);

}
