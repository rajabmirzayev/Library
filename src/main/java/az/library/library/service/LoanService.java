package az.library.library.service;

import az.library.library.dto.request.CreateLoanRequest;
import az.library.library.dto.request.UpdateLoanRequest;
import az.library.library.dto.response.LoanDetailedResponse;
import az.library.library.dto.response.LoanSummaryResponse;

import java.util.List;

public interface LoanService {

    LoanDetailedResponse create(CreateLoanRequest request);

    LoanDetailedResponse findById(Long id);

    List<LoanSummaryResponse> findAll();

    LoanDetailedResponse update(Long id, UpdateLoanRequest request);

    void delete(Long id);

}
