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
import az.library.library.service.LoanService;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoanServiceImpl implements LoanService {
    private final LoanRepository repo;
    private final BookCopyRepository bookCopyRepo;
    private final MemberRepository memberRepo;
    private final LoanMapper mapper;

    @Override
    @Transactional
    public LoanDetailedResponse create(CreateLoanRequest request) {
        BookCopy bookCopy = bookCopyRepo.findById(request.getBookCopyId())
                .orElseThrow(() -> new ResourceNotFoundException("BookCopy", request.getBookCopyId()));
        if (bookCopy.getStatus() != BookCopyStatus.AVAILABLE)
            throw new IllegalArgumentException("Book copy is not available for loan");
        Member member = memberRepo.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", request.getMemberId()));
        Loan loan = mapper.toEntityForCreate(request);
        loan.setBookCopy(bookCopy);
        loan.setMember(member);
        loan.setLoanDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.ACTIVE);
        bookCopy.setStatus(BookCopyStatus.LOANED);
        bookCopyRepo.save(bookCopy);
        return mapper.toDetailedResponse(repo.save(loan));
    }

    @Override
    public LoanDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", id)));
    }

    @Override
    public Page<LoanSummaryResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toSummaryResponse);
    }

    @Override
    @Transactional
    public LoanDetailedResponse update(Long id, UpdateLoanRequest request) {
        Loan entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan", id));
        if (request.getDueDate() != null) entity.setDueDate(request.getDueDate());
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Loan entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan", id));
        if (entity.getBookCopy() != null) {
            entity.getBookCopy().setStatus(BookCopyStatus.AVAILABLE);
            bookCopyRepo.save(entity.getBookCopy());
        }
        repo.delete(entity);
    }
}
