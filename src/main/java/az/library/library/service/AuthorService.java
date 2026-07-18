package az.library.library.service;

import az.library.library.dto.request.CreateAuthorRequest;
import az.library.library.dto.request.UpdateAuthorRequest;
import az.library.library.dto.response.AuthorDetailedResponse;
import az.library.library.dto.response.AuthorSummaryResponse;

import java.util.List;

public interface AuthorService {

    AuthorDetailedResponse create(CreateAuthorRequest request);

    AuthorDetailedResponse findById(Long id);

    List<AuthorSummaryResponse> findAll();

    AuthorDetailedResponse update(Long id, UpdateAuthorRequest request);

    void delete(Long id);

}
