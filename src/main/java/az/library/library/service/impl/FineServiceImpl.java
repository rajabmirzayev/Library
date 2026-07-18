package az.library.library.service.impl;

import az.library.library.dto.request.CreateFineRequest;
import az.library.library.dto.request.UpdateFineRequest;
import az.library.library.dto.response.FineDetailedResponse;
import az.library.library.dto.response.FineSummaryResponse;
import az.library.library.entity.Fine;
import az.library.library.enums.FineStatus;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.FineMapper;
import az.library.library.repository.FineRepository;
import az.library.library.repository.LoanRepository;
import az.library.library.repository.MemberRepository;
import az.library.library.service.FineService;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FineServiceImpl implements FineService {
    private final FineRepository repo;
    private final LoanRepository loanRepo;
    private final MemberRepository memberRepo;
    private final FineMapper mapper;

    @Override
    @Transactional
    public FineDetailedResponse create(CreateFineRequest request) {
        memberRepo.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", request.getMemberId()));
        Fine entity = mapper.toEntityForCreate(request);
        entity.setIssuedDate(LocalDateTime.now());
        entity.setStatus(FineStatus.PENDING);
        if (request.getLoanId() != null)
            entity.setLoan(loanRepo.findById(request.getLoanId())
                    .orElseThrow(() -> new ResourceNotFoundException("Loan", request.getLoanId())));
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    public FineDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fine", id)));
    }

    @Override
    public Page<FineSummaryResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toSummaryResponse);
    }

    @Override
    @Transactional
    public FineDetailedResponse update(Long id, UpdateFineRequest request) {
        Fine entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fine", id));
        if (request.getAmount() != null) entity.setAmount(request.getAmount());
        if (request.getReason() != null) entity.setReason(request.getReason());
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fine", id)));
    }
}
