package az.library.library.service;

import az.library.library.dto.response.LibraryOverviewResponse;
import az.library.library.dto.response.MostBorrowedBookResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    LibraryOverviewResponse getOverview();

    List<MostBorrowedBookResponse> getMostBorrowedBooks(int limit, LocalDate from, LocalDate to);
}
