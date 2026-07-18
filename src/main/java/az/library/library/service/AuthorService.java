package az.library.library.service;

import az.library.library.dto.request.CreateAuthorRequest;
import az.library.library.dto.request.UpdateAuthorRequest;
import az.library.library.dto.response.AuthorDetailedResponse;
import az.library.library.dto.response.AuthorSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    AuthorDetailedResponse create(CreateAuthorRequest request);

    AuthorDetailedResponse findById(Long id);

    Page<AuthorSummaryResponse> findAll(Pageable pageable);

    AuthorDetailedResponse update(Long id, UpdateAuthorRequest request);

    void delete(Long id);

}
