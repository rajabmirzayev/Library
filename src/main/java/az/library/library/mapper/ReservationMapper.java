package az.library.library.mapper;

import az.library.library.dto.request.CreateReservationRequest;
import az.library.library.dto.request.UpdateReservationRequest;
import az.library.library.dto.response.ReservationDetailedResponse;
import az.library.library.dto.response.ReservationSummaryResponse;
import az.library.library.entity.Book;
import az.library.library.entity.Member;
import az.library.library.entity.Reservation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "reservationDate", ignore = true)
    @Mapping(target = "queuePosition", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "book", source = "bookId", qualifiedByName = "refBook")
    @Mapping(target = "member", source = "memberId", qualifiedByName = "refMember")
    Reservation toEntityForCreate(CreateReservationRequest request);

    @Mapping(target = "bookId", expression = "java(r.getBook() != null ? r.getBook().getId() : null)")
    @Mapping(target = "bookTitle", expression = "java(r.getBook() != null ? r.getBook().getTitle() : null)")
    @Mapping(target = "memberId", expression = "java(r.getMember() != null ? r.getMember().getId() : null)")
    @Mapping(target = "memberName", expression = "java(r.getMember() != null ? r.getMember().getFirstName() + \" \" + r.getMember().getLastName() : null)")
    ReservationDetailedResponse toDetailedResponse(Reservation r);

    @Mapping(target = "bookTitle", expression = "java(r.getBook() != null ? r.getBook().getTitle() : null)")
    ReservationSummaryResponse toSummaryResponse(Reservation r);

    @Named("refBook")
    default Book refBook(Long id) {
        if (id == null) return null;
        Book b = new Book();
        b.setId(id);
        return b;
    }

    @Named("refMember")
    default Member refMember(Long id) {
        if (id == null) return null;
        Member m = new Member();
        m.setId(id);
        return m;
    }

}
