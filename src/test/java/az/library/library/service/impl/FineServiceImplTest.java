package az.library.library.service.impl;

import az.library.library.dto.request.CreateFineRequest;
import az.library.library.dto.request.UpdateFineRequest;
import az.library.library.dto.response.FineDetailedResponse;
import az.library.library.dto.response.FineSummaryResponse;
import az.library.library.entity.Fine;
import az.library.library.entity.Loan;
import az.library.library.entity.Member;
import az.library.library.enums.FineStatus;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.FineMapper;
import az.library.library.repository.FineRepository;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FineServiceImplTest {

    @Mock
    private FineRepository repo;
    @Mock
    private LoanRepository loanRepo;
    @Mock
    private MemberRepository memberRepo;
    @Mock
    private FineMapper mapper;

    @InjectMocks
    private FineServiceImpl service;

    @Test
    void Given_ValidRequestWithoutLoan_When_Create_Then_ReturnsDetailedResponse() {
        CreateFineRequest request = new CreateFineRequest();
        request.setMemberId(1L);
        request.setAmount(new BigDecimal("25.50"));
        request.setType("LATE_RETURN");

        Member member = Member.builder().id(1L).build();
        Fine entity = Fine.builder().build();
        FineDetailedResponse response = new FineDetailedResponse();
        response.setId(1L);

        given(memberRepo.findById(1L)).willReturn(Optional.of(member));
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        FineDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Fine> captor = ArgumentCaptor.forClass(Fine.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getIssuedDate()).isNotNull();
        assertThat(captor.getValue().getStatus()).isEqualTo(FineStatus.PENDING);
        assertThat(captor.getValue().getLoan()).isNull();
    }

    @Test
    void Given_ValidRequestWithLoan_When_Create_Then_SetsLoan() {
        CreateFineRequest request = new CreateFineRequest();
        request.setMemberId(1L);
        request.setLoanId(2L);
        request.setAmount(new BigDecimal("25.50"));

        Member member = Member.builder().id(1L).build();
        Loan loan = Loan.builder().id(2L).build();
        Fine entity = Fine.builder().build();
        FineDetailedResponse response = new FineDetailedResponse();

        given(memberRepo.findById(1L)).willReturn(Optional.of(member));
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(loanRepo.findById(2L)).willReturn(Optional.of(loan));
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        FineDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Fine> captor = ArgumentCaptor.forClass(Fine.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getLoan()).isEqualTo(loan);
    }

    @Test
    void Given_NonExistingMember_When_Create_Then_ThrowsResourceNotFoundException() {
        CreateFineRequest request = new CreateFineRequest();
        request.setMemberId(999L);

        given(memberRepo.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Member");
    }

    @Test
    void Given_NonExistingLoan_When_Create_Then_ThrowsResourceNotFoundException() {
        CreateFineRequest request = new CreateFineRequest();
        request.setMemberId(1L);
        request.setLoanId(999L);

        Member member = Member.builder().id(1L).build();
        given(memberRepo.findById(1L)).willReturn(Optional.of(member));
        given(mapper.toEntityForCreate(request)).willReturn(new Fine());
        given(loanRepo.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Loan");
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        Fine entity = Fine.builder().id(id).build();
        FineDetailedResponse response = new FineDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        FineDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Fine");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Fine entity = Fine.builder().id(1L).build();
        FineSummaryResponse summary = new FineSummaryResponse();
        summary.setId(1L);
        Page<Fine> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(repo.findAll(pageable)).willReturn(page);
        given(mapper.toSummaryResponse(entity)).willReturn(summary);

        Page<FineSummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void Given_AmountUpdate_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdateFineRequest request = new UpdateFineRequest();
        request.setAmount(new BigDecimal("30.00"));

        Fine entity = Fine.builder().id(id).amount(new BigDecimal("25.50")).build();
        FineDetailedResponse response = new FineDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(Fine.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        FineDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Fine> captor = ArgumentCaptor.forClass(Fine.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getAmount()).isEqualByComparingTo(new BigDecimal("30.00"));
    }

    @Test
    void Given_ReasonUpdate_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdateFineRequest request = new UpdateFineRequest();
        request.setReason("Zədələnmiş kitab");

        Fine entity = Fine.builder().id(id).build();
        FineDetailedResponse response = new FineDetailedResponse();

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(Fine.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        service.update(id, request);

        ArgumentCaptor<Fine> captor = ArgumentCaptor.forClass(Fine.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getReason()).isEqualTo("Zədələnmiş kitab");
    }

    @Test
    void Given_NonExistingId_When_Update_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        UpdateFineRequest request = new UpdateFineRequest();
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Fine");
    }

    @Test
    void Given_ExistingId_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        Fine entity = Fine.builder().id(id).build();
        given(repo.findById(id)).willReturn(Optional.of(entity));

        service.delete(id);

        verify(repo).delete(entity);
    }

    @Test
    void Given_NonExistingId_When_Delete_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Fine");
    }
}
