package az.library.library.service;

import az.library.library.dto.request.CreateReservationRequest;
import az.library.library.dto.request.UpdateReservationRequest;
import az.library.library.dto.response.ReservationDetailedResponse;
import az.library.library.dto.response.ReservationSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

    ReservationDetailedResponse create(CreateReservationRequest request);

    ReservationDetailedResponse findById(Long id);

    Page<ReservationSummaryResponse> findAll(Pageable pageable);

    ReservationDetailedResponse update(Long id, UpdateReservationRequest request);

    void delete(Long id);

}
