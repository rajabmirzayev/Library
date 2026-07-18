package az.library.library.service;

import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.request.UpdateBookRequest;
import az.library.library.dto.response.BookDetailedResponse;
import az.library.library.dto.response.BookSummaryResponse;

import java.util.List;

public interface BookService {

    BookDetailedResponse create(CreateBookRequest request);

    BookDetailedResponse findById(Long id);

    List<BookSummaryResponse> findAll();

    BookDetailedResponse update(Long id, UpdateBookRequest request);

    void delete(Long id);

}
