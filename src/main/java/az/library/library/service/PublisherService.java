package az.library.library.service;

import az.library.library.dto.request.CreatePublisherRequest;
import az.library.library.dto.request.UpdatePublisherRequest;
import az.library.library.dto.response.PublisherDetailedResponse;
import az.library.library.dto.response.PublisherSummaryResponse;

import java.util.List;

public interface PublisherService {

    PublisherDetailedResponse create(CreatePublisherRequest request);

    PublisherDetailedResponse findById(Long id);

    List<PublisherSummaryResponse> findAll();

    PublisherDetailedResponse update(Long id, UpdatePublisherRequest request);

    void delete(Long id);

}
