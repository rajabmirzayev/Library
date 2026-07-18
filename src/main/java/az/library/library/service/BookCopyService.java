package az.library.library.service;

import az.library.library.dto.request.CreateBookCopyRequest;
import az.library.library.dto.request.UpdateBookCopyRequest;
import az.library.library.dto.response.BookCopyDetailedResponse;
import az.library.library.dto.response.BookCopySummaryResponse;

import java.util.List;

public interface BookCopyService {

    BookCopyDetailedResponse create(CreateBookCopyRequest request);

    BookCopyDetailedResponse findById(Long id);

    List<BookCopySummaryResponse> findAll();

    BookCopyDetailedResponse update(Long id, UpdateBookCopyRequest request);

    void delete(Long id);

}
