package az.library.library.service;

import az.library.library.dto.request.CreateBookCopyRequest;
import az.library.library.dto.request.UpdateBookCopyRequest;
import az.library.library.dto.response.BookCopyDetailedResponse;
import az.library.library.dto.response.BookCopySummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCopyService {

    BookCopyDetailedResponse create(CreateBookCopyRequest request);

    BookCopyDetailedResponse findById(Long id);

    Page<BookCopySummaryResponse> findAll(Pageable pageable);

    BookCopyDetailedResponse update(Long id, UpdateBookCopyRequest request);

    void delete(Long id);

}
