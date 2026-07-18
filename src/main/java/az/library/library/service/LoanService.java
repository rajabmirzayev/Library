package az.library.library.service;

import az.library.library.dto.request.CreateLoanRequest;
import az.library.library.dto.request.UpdateLoanRequest;
import az.library.library.dto.response.LoanDetailedResponse;
import az.library.library.dto.response.LoanSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {

    LoanDetailedResponse create(CreateLoanRequest request);

    LoanDetailedResponse findById(Long id);

    Page<LoanSummaryResponse> findAll(Pageable pageable);

    LoanDetailedResponse update(Long id, UpdateLoanRequest request);

    void delete(Long id);

}
