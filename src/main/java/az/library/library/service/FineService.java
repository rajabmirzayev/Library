package az.library.library.service;

import az.library.library.dto.request.CreateFineRequest;
import az.library.library.dto.request.UpdateFineRequest;
import az.library.library.dto.response.FineDetailedResponse;
import az.library.library.dto.response.FineSummaryResponse;

import java.util.List;

public interface FineService {

    FineDetailedResponse create(CreateFineRequest request);

    FineDetailedResponse findById(Long id);

    List<FineSummaryResponse> findAll();

    FineDetailedResponse update(Long id, UpdateFineRequest request);

    void delete(Long id);

}
