package az.library.library.service;

import az.library.library.dto.request.CreateReservationRequest;
import az.library.library.dto.request.UpdateReservationRequest;
import az.library.library.dto.response.ReservationDetailedResponse;
import az.library.library.dto.response.ReservationSummaryResponse;

import java.util.List;

public interface ReservationService {

    ReservationDetailedResponse create(CreateReservationRequest request);

    ReservationDetailedResponse findById(Long id);

    List<ReservationSummaryResponse> findAll();

    ReservationDetailedResponse update(Long id, UpdateReservationRequest request);

    void delete(Long id);

}
