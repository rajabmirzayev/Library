package az.library.library.mapper;

import az.library.library.dto.request.CreateLoanRequest;
import az.library.library.dto.request.UpdateLoanRequest;
import az.library.library.dto.response.LoanDetailedResponse;
import az.library.library.dto.response.LoanSummaryResponse;
import az.library.library.entity.BookCopy;
import az.library.library.entity.Loan;
import az.library.library.entity.Member;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "returnDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "fines", ignore = true)
    @Mapping(target = "loanDate", ignore = true)
    @Mapping(target = "bookCopy", source = "bookCopyId", qualifiedByName = "refBookCopy")
    @Mapping(target = "member", source = "memberId", qualifiedByName = "refMember")
    Loan toEntityForCreate(CreateLoanRequest request);

    @Mapping(target = "bookCopyId", expression = "java(loan.getBookCopy() != null ? loan.getBookCopy().getId() : null)")
    @Mapping(target = "bookBarcode", expression = "java(loan.getBookCopy() != null ? loan.getBookCopy().getBarcode() : null)")
    @Mapping(target = "bookTitle", expression = "java(loan.getBookCopy() != null && loan.getBookCopy().getBook() != null ? loan.getBookCopy().getBook().getTitle() : null)")
    @Mapping(target = "memberId", expression = "java(loan.getMember() != null ? loan.getMember().getId() : null)")
    @Mapping(target = "memberName", expression = "java(loan.getMember() != null ? loan.getMember().getFirstName() + \" \" + loan.getMember().getLastName() : null)")
    LoanDetailedResponse toDetailedResponse(Loan loan);

    @Mapping(target = "bookTitle", expression = "java(loan.getBookCopy() != null && loan.getBookCopy().getBook() != null ? loan.getBookCopy().getBook().getTitle() : null)")
    @Mapping(target = "memberName", expression = "java(loan.getMember() != null ? loan.getMember().getFirstName() + \" \" + loan.getMember().getLastName() : null)")
    LoanSummaryResponse toSummaryResponse(Loan loan);

    @Named("refBookCopy")
    default BookCopy refBookCopy(Long id) {
        if (id == null) return null;
        BookCopy bc = new BookCopy();
        bc.setId(id);
        return bc;
    }

    @Named("refMember")
    default Member refMember(Long id) {
        if (id == null) return null;
        Member m = new Member();
        m.setId(id);
        return m;
    }

}
