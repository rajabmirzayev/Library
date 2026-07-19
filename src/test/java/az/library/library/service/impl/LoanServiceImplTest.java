package az.library.library.service.impl;

import az.library.library.dto.request.CreateLoanRequest;
import az.library.library.dto.request.UpdateLoanRequest;
import az.library.library.dto.response.LoanDetailedResponse;
import az.library.library.dto.response.LoanSummaryResponse;
import az.library.library.entity.BookCopy;
import az.library.library.entity.Loan;
import az.library.library.entity.Member;
import az.library.library.enums.BookCopyStatus;
import az.library.library.enums.LoanStatus;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.LoanMapper;
import az.library.library.repository.BookCopyRepository;
import az.library.library.repository.LoanRepository;
import az.library.library.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanRepository repo;
    @Mock
    private BookCopyRepository bookCopyRepo;
    @Mock
    private MemberRepository memberRepo;
    @Mock
    private LoanMapper mapper;

    @InjectMocks
    private LoanServiceImpl service;

    @Test
    void Given_AvailableBookCopy_When_Create_Then_ReturnsDetailedResponse() {
        CreateLoanRequest request = new CreateLoanRequest();
        request.setBookCopyId(1L);
        request.setMemberId(1L);
        request.setDueDate(LocalDate.of(2026, 8, 15));

        BookCopy bookCopy = BookCopy.builder().id(1L).status(BookCopyStatus.AVAILABLE).build();
        Member member = Member.builder().id(1L).build();
        Loan entity = Loan.builder().build();
        LoanDetailedResponse response = new LoanDetailedResponse();
        response.setId(1L);

        given(bookCopyRepo.findById(1L)).willReturn(Optional.of(bookCopy));
        given(memberRepo.findById(1L)).willReturn(Optional.of(member));
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(bookCopyRepo.save(any(BookCopy.class))).willReturn(bookCopy);
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        LoanDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        ArgumentCaptor<BookCopy> bookCopyCaptor = ArgumentCaptor.forClass(BookCopy.class);
        verify(bookCopyRepo).save(bookCopyCaptor.capture());
        assertThat(bookCopyCaptor.getValue().getStatus()).isEqualTo(BookCopyStatus.LOANED);
        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(repo).save(loanCaptor.capture());
        assertThat(loanCaptor.getValue().getBookCopy()).isEqualTo(bookCopy);
        assertThat(loanCaptor.getValue().getMember()).isEqualTo(member);
        assertThat(loanCaptor.getValue().getStatus()).isEqualTo(LoanStatus.ACTIVE);
    }

    @Test
    void Given_UnavailableBookCopy_When_Create_Then_ThrowsIllegalArgumentException() {
        CreateLoanRequest request = new CreateLoanRequest();
        request.setBookCopyId(1L);

        BookCopy bookCopy = BookCopy.builder().id(1L).status(BookCopyStatus.LOANED).build();
        given(bookCopyRepo.findById(1L)).willReturn(Optional.of(bookCopy));

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not available");
    }

    @Test
    void Given_NonExistingBookCopy_When_Create_Then_ThrowsResourceNotFoundException() {
        CreateLoanRequest request = new CreateLoanRequest();
        request.setBookCopyId(999L);

        given(bookCopyRepo.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("BookCopy");
    }

    @Test
    void Given_NonExistingMember_When_Create_Then_ThrowsResourceNotFoundException() {
        CreateLoanRequest request = new CreateLoanRequest();
        request.setBookCopyId(1L);
        request.setMemberId(999L);

        BookCopy bookCopy = BookCopy.builder().id(1L).status(BookCopyStatus.AVAILABLE).build();
        given(bookCopyRepo.findById(1L)).willReturn(Optional.of(bookCopy));
        given(memberRepo.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Member");
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        Loan entity = Loan.builder().id(id).build();
        LoanDetailedResponse response = new LoanDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        LoanDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Loan");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Loan entity = Loan.builder().id(1L).build();
        LoanSummaryResponse summary = new LoanSummaryResponse();
        summary.setId(1L);
        Page<Loan> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(repo.findAll(pageable)).willReturn(page);
        given(mapper.toSummaryResponse(entity)).willReturn(summary);

        Page<LoanSummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void Given_ValidRequest_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdateLoanRequest request = new UpdateLoanRequest();
        request.setDueDate(LocalDate.of(2026, 9, 1));

        Loan entity = Loan.builder().id(id).build();
        LoanDetailedResponse response = new LoanDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(Loan.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        LoanDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Loan> captor = ArgumentCaptor.forClass(Loan.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getDueDate()).isEqualTo(LocalDate.of(2026, 9, 1));
    }

    @Test
    void Given_NonExistingId_When_Update_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        UpdateLoanRequest request = new UpdateLoanRequest();
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Loan");
    }

    @Test
    void Given_LoanWithBookCopy_When_Delete_Then_RestoresBookCopyStatusAndDeletes() {
        Long id = 1L;
        BookCopy bookCopy = BookCopy.builder().id(1L).status(BookCopyStatus.LOANED).build();
        Loan entity = Loan.builder().id(id).bookCopy(bookCopy).build();

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(bookCopyRepo.save(any(BookCopy.class))).willReturn(bookCopy);

        service.delete(id);

        ArgumentCaptor<BookCopy> captor = ArgumentCaptor.forClass(BookCopy.class);
        verify(bookCopyRepo).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(BookCopyStatus.AVAILABLE);
        verify(bookCopyRepo).save(bookCopy);
        verify(repo).delete(entity);
    }

    @Test
    void Given_LoanWithoutBookCopy_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        Loan entity = Loan.builder().id(id).bookCopy(null).build();

        given(repo.findById(id)).willReturn(Optional.of(entity));

        service.delete(id);

        verify(bookCopyRepo, never()).save(any());
        verify(repo).delete(entity);
    }

    @Test
    void Given_NonExistingId_When_Delete_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Loan");
    }
}
