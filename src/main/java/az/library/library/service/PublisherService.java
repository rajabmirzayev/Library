package az.library.library.service;

import az.library.library.dto.request.CreatePublisherRequest;
import az.library.library.dto.request.UpdatePublisherRequest;
import az.library.library.dto.response.PublisherDetailedResponse;
import az.library.library.dto.response.PublisherSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PublisherService {

    PublisherDetailedResponse create(CreatePublisherRequest request);

    PublisherDetailedResponse findById(Long id);

    Page<PublisherSummaryResponse> findAll(Pageable pageable);

    PublisherDetailedResponse update(Long id, UpdatePublisherRequest request);

    void delete(Long id);

}
