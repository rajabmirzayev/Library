package az.library.library.controller;

import az.library.library.dto.ApiResponse;
import az.library.library.dto.request.CreateReservationRequest;
import az.library.library.dto.request.UpdateReservationRequest;
import az.library.library.dto.response.PageResponse;
import az.library.library.dto.response.ReservationDetailedResponse;
import az.library.library.dto.response.ReservationSummaryResponse;
import az.library.library.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationDetailedResponse>> create(@Valid @RequestBody CreateReservationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(service.create(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationDetailedResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ReservationSummaryResponse>>> findAll(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(PageResponse.of(service.findAll(pageable))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationDetailedResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateReservationRequest req) {
        return ResponseEntity.ok(ApiResponse.success(service.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
