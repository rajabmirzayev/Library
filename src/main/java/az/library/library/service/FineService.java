package az.library.library.service;

import az.library.library.dto.request.CreateFineRequest;
import az.library.library.dto.request.UpdateFineRequest;
import az.library.library.dto.response.FineDetailedResponse;
import az.library.library.dto.response.FineSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FineService {

    FineDetailedResponse create(CreateFineRequest request);

    FineDetailedResponse findById(Long id);

    Page<FineSummaryResponse> findAll(Pageable pageable);

    FineDetailedResponse update(Long id, UpdateFineRequest request);

    void delete(Long id);

}
