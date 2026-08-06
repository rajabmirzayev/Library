package az.library.library.service.impl;

import az.library.library.dto.response.LibraryOverviewResponse;
import az.library.library.dto.response.MostBorrowedBookResponse;
import az.library.library.repository.ReportRepository;
import az.library.library.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public LibraryOverviewResponse getOverview() {
        ReportRepository.LibraryOverview o = reportRepository.findOverview();
        return new LibraryOverviewResponse(
                o.getTotalBooks(),
                o.getTotalCopies(),
                o.getAvailableCopies(),
                o.getTotalMembers(),
                o.getActiveLoans(),
                o.getOverdueLoans(),
                o.getPendingReservations(),
                o.getPendingFines(),
                o.getCollectedFineRevenue(),
                o.getTotalFineAmount());
    }

    @Override
    public List<MostBorrowedBookResponse> getMostBorrowedBooks(int limit, LocalDate from, LocalDate to) {
        List<ReportRepository.MostBorrowedBook> results;
        if (from != null && to != null) {
            results = reportRepository.findMostBorrowedBooksWithDates(limit, from, to);
        } else {
            results = reportRepository.findMostBorrowedBooks(limit);
        }
        return results.stream()
                .map(r -> new MostBorrowedBookResponse(
                        r.getBookId(),
                        r.getBookTitle(),
                        r.getIsbn(),
                        r.getBorrowCount(),
                        r.getAuthorNames()))
                .toList();
    }
}
