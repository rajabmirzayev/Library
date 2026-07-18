package az.library.library.service.impl;

import az.library.library.dto.request.CreateReservationRequest;
import az.library.library.dto.request.UpdateReservationRequest;
import az.library.library.dto.response.ReservationDetailedResponse;
import az.library.library.dto.response.ReservationSummaryResponse;
import az.library.library.entity.Book;
import az.library.library.entity.Member;
import az.library.library.entity.Reservation;
import az.library.library.enums.ReservationStatus;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.ReservationMapper;
import az.library.library.repository.BookRepository;
import az.library.library.repository.MemberRepository;
import az.library.library.repository.ReservationRepository;
import az.library.library.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository repo;
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final ReservationMapper mapper;

    @Override
    @Transactional
    public ReservationDetailedResponse create(CreateReservationRequest request) {
        Book book = bookRepo.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", request.getBookId()));
        Member member = memberRepo.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", request.getMemberId()));
        Reservation entity = mapper.toEntityForCreate(request);
        entity.setBook(book);
        entity.setMember(member);
        entity.setReservationDate(LocalDateTime.now());
        entity.setQueuePosition(repo.findByBookId(request.getBookId()).size() + 1);
        entity.setStatus(ReservationStatus.PENDING);
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    public ReservationDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id)));
    }

    @Override
    public List<ReservationSummaryResponse> findAll() {
        return repo.findAll().stream().map(mapper::toSummaryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservationDetailedResponse update(Long id, UpdateReservationRequest request) {
        Reservation entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        if (request.getExpiryDate() != null) entity.setExpiryDate(request.getExpiryDate());
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id)));
    }
}
