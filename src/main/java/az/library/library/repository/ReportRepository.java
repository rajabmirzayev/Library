package az.library.library.repository;

import az.library.library.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@RepositoryDefinition(domainClass = Book.class, idClass = Long.class)
public interface ReportRepository {

    @Query(value = """
            SELECT
                (SELECT COUNT(*) FROM books WHERE deleted = false)                                AS "totalBooks",
                (SELECT COUNT(*) FROM book_copies WHERE deleted = false)                           AS "totalCopies",
                (SELECT COUNT(*) FROM book_copies WHERE deleted = false AND status = 'AVAILABLE')  AS "availableCopies",
                (SELECT COUNT(*) FROM members WHERE deleted = false)                               AS "totalMembers",
                (SELECT COUNT(*) FROM loans WHERE deleted = false AND return_date IS NULL)         AS "activeLoans",
                (SELECT COUNT(*) FROM loans WHERE deleted = false
                                              AND return_date IS NULL
                                              AND status = 'ACTIVE'
                                              AND due_date < CURRENT_DATE)      AS "overdueLoans",
                (SELECT COUNT(*) FROM reservations WHERE deleted = false AND status = 'PENDING')   AS "pendingReservations",
                (SELECT COUNT(*) FROM fines WHERE deleted = false AND status = 'PENDING')          AS "pendingFines",
                (SELECT COALESCE(SUM(amount), 0) FROM fines
                     WHERE deleted = false AND status = 'PAID')                                    AS "collectedFineRevenue",
                (SELECT COALESCE(SUM(amount), 0) FROM fines WHERE deleted = false)                 AS "totalFineAmount"
            """, nativeQuery = true)
    LibraryOverview findOverview();

    @Query(value = """
            SELECT
                b.id                                   AS "bookId",
                b.title                                AS "bookTitle",
                b.isbn                                 AS "isbn",
                COUNT(l.id)                            AS "borrowCount",
                COALESCE(STRING_AGG(DISTINCT a.first_name || ' ' || a.last_name, ', '), '') AS "authorNames"
            FROM loans l
            JOIN book_copies bc ON bc.id = l.book_copy_id AND bc.deleted = false
            JOIN books b       ON b.id = bc.book_id       AND b.deleted = false
            LEFT JOIN book_authors ba ON ba.book_id = b.id
            LEFT JOIN authors a      ON a.id = ba.author_id AND a.deleted = false
            WHERE l.deleted = false
            GROUP BY b.id, b.title, b.isbn
            ORDER BY "borrowCount" DESC, b.title ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<MostBorrowedBook> findMostBorrowedBooks(@Param("limit") int limit);

    @Query(value = """
            SELECT
                b.id                                   AS "bookId",
                b.title                                AS "bookTitle",
                b.isbn                                 AS "isbn",
                COUNT(l.id)                            AS "borrowCount",
                COALESCE(STRING_AGG(DISTINCT a.first_name || ' ' || a.last_name, ', '), '') AS "authorNames"
            FROM loans l
            JOIN book_copies bc ON bc.id = l.book_copy_id AND bc.deleted = false
            JOIN books b       ON b.id = bc.book_id       AND b.deleted = false
            LEFT JOIN book_authors ba ON ba.book_id = b.id
            LEFT JOIN authors a      ON a.id = ba.author_id AND a.deleted = false
            WHERE l.deleted = false
              AND l.loan_date >= cast(:fromDate as date)
              AND l.loan_date < cast(:toDate as date) + 1
            GROUP BY b.id, b.title, b.isbn
            ORDER BY "borrowCount" DESC, b.title ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<MostBorrowedBook> findMostBorrowedBooksWithDates(@Param("limit") int limit,
                                                           @Param("fromDate") LocalDate fromDate,
                                                           @Param("toDate") LocalDate toDate);

    interface LibraryOverview {
        long getTotalBooks();
        long getTotalCopies();
        long getAvailableCopies();
        long getTotalMembers();
        long getActiveLoans();
        long getOverdueLoans();
        long getPendingReservations();
        long getPendingFines();
        BigDecimal getCollectedFineRevenue();
        BigDecimal getTotalFineAmount();
    }

    interface MostBorrowedBook {
        Long getBookId();
        String getBookTitle();
        String getIsbn();
        Long getBorrowCount();
        String getAuthorNames();
    }
}
