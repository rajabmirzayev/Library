package az.library.library.service.impl;

import az.library.library.dto.request.CreateBookRequest;
import az.library.library.dto.request.CreateLoanRequest;
import az.library.library.entity.*;
import az.library.library.enums.BookCopyCondition;
import az.library.library.enums.BookCopyStatus;
import az.library.library.enums.BookStatus;
import az.library.library.enums.LoanStatus;
import az.library.library.enums.MemberStatus;
import az.library.library.repository.*;
import az.library.library.service.BookService;
import az.library.library.service.LoanService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class TransactionRollbackIntegrationTest {

    @Autowired
    private TransactionTemplate tx;

    @Autowired
    private BookService bookService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private BookCopyRepository bookCopyRepo;

    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private PublisherRepository publisherRepo;

    @Autowired
    private MemberRepository memberRepo;

    private static class SimulatedDbFailure extends RuntimeException {
        SimulatedDbFailure(String msg) {
            super(msg);
        }
    }

    private Long authorId;
    private Long categoryId;
    private Long publisherId;
    private Long memberId;
    private static final AtomicInteger SEQ = new AtomicInteger(1);

    @BeforeEach
    void setUp() {
        int seq = SEQ.getAndIncrement();
        tx.executeWithoutResult(status -> {
            authorId = authorRepo.save(Author.builder()
                    .firstName("Nizami" + seq).lastName("Gencevi").build()).getId();
            categoryId = categoryRepo.save(Category.builder()
                    .name("Klassik" + seq).build()).getId();
            publisherId = publisherRepo.save(Publisher.builder()
                    .name("Azernesr" + seq).build()).getId();
            memberId = memberRepo.save(Member.builder()
                    .firstName("Ali").lastName("Hesenov").email("ali" + seq + "@test.az").phone("+994501234567")
                    .membershipNumber("MEM" + String.format("%05d", seq)).membershipDate(LocalDateTime.now())
                    .status(MemberStatus.ACTIVE).build()).getId();
        });
    }

    @AfterEach
    void tearDown() {
        tx.executeWithoutResult(status -> {
            loanRepo.findAll().forEach(loanRepo::delete);
            bookCopyRepo.findAll().forEach(bookCopyRepo::delete);
            bookRepo.findAll().forEach(bookRepo::delete);
            memberRepo.findAll().forEach(memberRepo::delete);
            categoryRepo.findAll().forEach(categoryRepo::delete);
            authorRepo.findAll().forEach(authorRepo::delete);
            publisherRepo.findAll().forEach(publisherRepo::delete);
        });
    }

    @Test
    void Given_MultipleWrites_When_RuntimeExceptionThrown_Then_EverythingRolledBack() {
        Long copyId = tx.execute(status -> {
            Book book = bookRepo.save(Book.builder()
                    .title("Test Kitab").isbn("978-TEST-RT-01").status(BookStatus.AVAILABLE).build());
            BookCopy copy = bookCopyRepo.save(BookCopy.builder()
                    .book(book).condition(BookCopyCondition.NEW).barcode("BC-RT-01").status(BookCopyStatus.AVAILABLE).build());
            return copy.getId();
        });

        assertThatThrownBy(() -> tx.executeWithoutResult(status -> {
            BookCopy copy = bookCopyRepo.findById(copyId).orElseThrow();
            copy.setStatus(BookCopyStatus.LOANED);
            bookCopyRepo.save(copy);
            throw new RuntimeException("Simulated crash after first write");
        })).isInstanceOf(RuntimeException.class);

        BookCopy reloaded = tx.execute(status -> bookCopyRepo.findById(copyId).orElseThrow());
        assertThat(reloaded.getStatus()).isEqualTo(BookCopyStatus.AVAILABLE);
    }

    @Test
    void Given_MultipleWrites_When_CheckedExceptionThrown_Then_EverythingRolledBack() {
        Long copyId = tx.execute(status -> {
            Book book = bookRepo.save(Book.builder()
                    .title("Checked Ex Test").isbn("978-TEST-CE-01").status(BookStatus.AVAILABLE).build());
            return bookCopyRepo.save(BookCopy.builder()
                    .book(book).condition(BookCopyCondition.NEW).barcode("BC-CE-01").status(BookCopyStatus.AVAILABLE).build()).getId();
        });

        assertThatThrownBy(() -> tx.executeWithoutResult(status -> {
            BookCopy copy = bookCopyRepo.findById(copyId).orElseThrow();
            copy.setStatus(BookCopyStatus.LOANED);
            bookCopyRepo.save(copy);
            status.setRollbackOnly();
            throw new SimulatedDbFailure("Simulated DB failure");
        })).isInstanceOf(SimulatedDbFailure.class);

        BookCopy reloaded = tx.execute(status -> bookCopyRepo.findById(copyId).orElseThrow());
        assertThat(reloaded.getStatus()).isEqualTo(BookCopyStatus.AVAILABLE);
    }

    @Test
    void Given_ValidLoanRequest_When_LoanServiceCalled_Then_LoanCreatedAndBookCopyUpdated() {
        Long copyId = tx.execute(status -> {
            Book book = bookRepo.save(Book.builder()
                    .title("Atomic Loan").isbn("978-TEST-AL-01").status(BookStatus.AVAILABLE).build());
            return bookCopyRepo.save(BookCopy.builder()
                    .book(book).condition(BookCopyCondition.NEW).barcode("BC-AL-01").status(BookCopyStatus.AVAILABLE).build()).getId();
        });

        var request = CreateLoanRequest.builder()
                .bookCopyId(copyId).memberId(memberId)
                .dueDate(LocalDate.now().plusDays(7)).build();

        tx.executeWithoutResult(status -> loanService.create(request));

        tx.executeWithoutResult(status -> {
            BookCopy copy = bookCopyRepo.findById(copyId).orElseThrow();
            assertThat(copy.getStatus()).isEqualTo(BookCopyStatus.LOANED);
            assertThat(loanRepo.count()).isEqualTo(1);
        });
    }

    @Test
    void Given_LoanedBookCopy_When_LoanCreateCalledAgain_Then_NothingPersisted() {
        Long copyId = tx.execute(status -> {
            Book book = bookRepo.save(Book.builder()
                    .title("Already Loaned").isbn("978-TEST-A2-01").status(BookStatus.AVAILABLE).build());
            BookCopy copy = bookCopyRepo.save(BookCopy.builder()
                    .book(book).condition(BookCopyCondition.NEW).barcode("BC-A2-01").status(BookCopyStatus.AVAILABLE).build());
            Loan loan = Loan.builder().bookCopy(copy)
                    .member(memberRepo.findById(memberId).orElseThrow())
                    .loanDate(LocalDateTime.now()).dueDate(LocalDate.now().plusDays(7))
                    .status(LoanStatus.ACTIVE).build();
            loanRepo.save(loan);
            copy.setStatus(BookCopyStatus.LOANED);
            bookCopyRepo.save(copy);
            return copy.getId();
        });

        var request = CreateLoanRequest.builder()
                .bookCopyId(copyId).memberId(memberId)
                .dueDate(LocalDate.now().plusDays(7)).build();

        assertThatThrownBy(() -> tx.executeWithoutResult(status -> {
            loanService.create(request);
        })).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not available");

        long loanCount = tx.execute(status -> loanRepo.count());
        assertThat(loanCount).isEqualTo(1);
    }

    @Test
    void Given_ActiveLoan_When_DeleteCalled_Then_LoanDeletedAndBookCopyAvailable() {
        Long copyId = tx.execute(status -> {
            Book book = bookRepo.save(Book.builder()
                    .title("Delete Rollback").isbn("978-TEST-DR-01").status(BookStatus.AVAILABLE).build());
            BookCopy copy = bookCopyRepo.save(BookCopy.builder()
                    .book(book).condition(BookCopyCondition.NEW).barcode("BC-DR-01").status(BookCopyStatus.AVAILABLE).build());
            Loan loan = Loan.builder().bookCopy(copy)
                    .member(memberRepo.findById(memberId).orElseThrow())
                    .loanDate(LocalDateTime.now()).dueDate(LocalDate.now().plusDays(7))
                    .status(LoanStatus.ACTIVE).build();
            loan.setBookCopy(copy);
            loanRepo.save(loan);
            copy.setStatus(BookCopyStatus.LOANED);
            bookCopyRepo.save(copy);
            return copy.getId();
        });

        Long loanId = tx.execute(status ->
                loanRepo.findByBookCopyId(copyId).get(0).getId());

        tx.executeWithoutResult(status -> loanService.delete(loanId));

        tx.executeWithoutResult(status -> {
            BookCopy copy = bookCopyRepo.findById(copyId).orElseThrow();
            assertThat(copy.getStatus()).isEqualTo(BookCopyStatus.AVAILABLE);
            assertThat(loanRepo.count()).isEqualTo(0);
        });
    }

    @Test
    void Given_ValidBookRequest_When_BookServiceCreateCalled_Then_BookPersistedWithAssociations() {
        var request = CreateBookRequest.builder()
                .title("Book Tx Test").isbn("978-TEST-BT-01").publicationYear(2026)
                .price(BigDecimal.valueOf(19.99)).language("az")
                .authorIds(Set.of(authorId)).categoryIds(Set.of(categoryId)).publisherId(publisherId)
                .build();

        tx.executeWithoutResult(status -> bookService.create(request));

        long count = tx.execute(status -> bookRepo.count());
        assertThat(count).isEqualTo(1);
    }

    @Test
    void Given_DuplicateIsbn_When_BookServiceCreateCalled_Then_NothingPersisted() {
        var request = CreateBookRequest.builder()
                .title("Dup ISBN").isbn("978-TEST-DUP-01").publicationYear(2026)
                .price(BigDecimal.valueOf(9.99)).language("az")
                .authorIds(Set.of(authorId)).categoryIds(Set.of(categoryId)).publisherId(publisherId)
                .build();

        tx.executeWithoutResult(status -> bookService.create(request));

        assertThatThrownBy(() -> tx.executeWithoutResult(status -> {
            bookService.create(request);
        })).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");

        long count = tx.execute(status -> bookRepo.count());
        assertThat(count).isEqualTo(1);
    }
}
