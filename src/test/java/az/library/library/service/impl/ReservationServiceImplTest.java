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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository repo;
    @Mock
    private BookRepository bookRepo;
    @Mock
    private MemberRepository memberRepo;
    @Mock
    private ReservationMapper mapper;

    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    void Given_ValidRequest_When_Create_Then_ReturnsDetailedResponse() {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setBookId(1L);
        request.setMemberId(1L);
        request.setExpiryDate(LocalDate.of(2026, 8, 1));

        Book book = Book.builder().id(1L).build();
        Member member = Member.builder().id(1L).build();
        Reservation entity = Reservation.builder().build();
        ReservationDetailedResponse response = new ReservationDetailedResponse();
        response.setId(1L);

        given(bookRepo.findById(1L)).willReturn(Optional.of(book));
        given(memberRepo.findById(1L)).willReturn(Optional.of(member));
        given(repo.findByBookId(1L)).willReturn(Collections.emptyList());
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        ReservationDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getBook()).isEqualTo(book);
        assertThat(captor.getValue().getMember()).isEqualTo(member);
        assertThat(captor.getValue().getReservationDate()).isNotNull();
        assertThat(captor.getValue().getQueuePosition()).isEqualTo(1);
        assertThat(captor.getValue().getStatus()).isEqualTo(ReservationStatus.PENDING);
    }

    @Test
    void Given_ExistingReservations_When_Create_Then_SetsCorrectQueuePosition() {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setBookId(1L);
        request.setMemberId(1L);

        Book book = Book.builder().id(1L).build();
        Member member = Member.builder().id(1L).build();
        Reservation existing = Reservation.builder().id(1L).build();
        Reservation entity = Reservation.builder().build();
        ReservationDetailedResponse response = new ReservationDetailedResponse();

        given(bookRepo.findById(1L)).willReturn(Optional.of(book));
        given(memberRepo.findById(1L)).willReturn(Optional.of(member));
        given(repo.findByBookId(1L)).willReturn(List.of(existing));
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.save(entity)).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        service.create(request);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getQueuePosition()).isEqualTo(2);
    }

    @Test
    void Given_NonExistingBook_When_Create_Then_ThrowsResourceNotFoundException() {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setBookId(999L);

        given(bookRepo.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book");
    }

    @Test
    void Given_NonExistingMember_When_Create_Then_ThrowsResourceNotFoundException() {
        CreateReservationRequest request = new CreateReservationRequest();
        request.setBookId(1L);
        request.setMemberId(999L);

        Book book = Book.builder().id(1L).build();
        given(bookRepo.findById(1L)).willReturn(Optional.of(book));
        given(memberRepo.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Member");
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        Reservation entity = Reservation.builder().id(id).build();
        ReservationDetailedResponse response = new ReservationDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        ReservationDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Reservation");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Reservation entity = Reservation.builder().id(1L).build();
        ReservationSummaryResponse summary = new ReservationSummaryResponse();
        summary.setId(1L);
        Page<Reservation> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(repo.findAll(pageable)).willReturn(page);
        given(mapper.toSummaryResponse(entity)).willReturn(summary);

        Page<ReservationSummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void Given_ExpiryDateUpdate_When_Update_Then_ReturnsUpdatedResponse() {
        Long id = 1L;
        UpdateReservationRequest request = new UpdateReservationRequest();
        request.setExpiryDate(LocalDate.of(2026, 9, 1));

        Reservation entity = Reservation.builder().id(id).build();
        ReservationDetailedResponse response = new ReservationDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(Reservation.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        ReservationDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getExpiryDate()).isEqualTo(LocalDate.of(2026, 9, 1));
    }

    @Test
    void Given_NonExistingId_When_Update_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        UpdateReservationRequest request = new UpdateReservationRequest();
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Reservation");
    }

    @Test
    void Given_ExistingId_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        Reservation entity = Reservation.builder().id(id).build();
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
                .hasMessageContaining("Reservation");
    }
}
