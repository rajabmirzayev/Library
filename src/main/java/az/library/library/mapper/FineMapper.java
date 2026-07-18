package az.library.library.mapper;

import az.library.library.dto.request.CreateFineRequest;
import az.library.library.dto.request.UpdateFineRequest;
import az.library.library.dto.response.FineDetailedResponse;
import az.library.library.dto.response.FineSummaryResponse;
import az.library.library.entity.Fine;
import az.library.library.entity.Loan;
import az.library.library.entity.Member;
import az.library.library.enums.FineStatus;
import az.library.library.enums.FineType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FineMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "paidDate", ignore = true)
    @Mapping(target = "issuedDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "type", source = "type", qualifiedByName = "mapType")
    @Mapping(target = "loan", source = "loanId", qualifiedByName = "refLoan")
    @Mapping(target = "member", source = "memberId", qualifiedByName = "refMember")
    Fine toEntityForCreate(CreateFineRequest request);

    @Mapping(target = "loanId", expression = "java(f.getLoan() != null ? f.getLoan().getId() : null)")
    @Mapping(target = "memberId", expression = "java(f.getMember() != null ? f.getMember().getId() : null)")
    @Mapping(target = "memberName", expression = "java(f.getMember() != null ? f.getMember().getFirstName() + \" \" + f.getMember().getLastName() : null)")
    FineDetailedResponse toDetailedResponse(Fine f);

    @Mapping(target = "memberName", expression = "java(f.getMember() != null ? f.getMember().getFirstName() + \" \" + f.getMember().getLastName() : null)")
    FineSummaryResponse toSummaryResponse(Fine f);

    @Named("mapType")
    default FineType mapType(String t) {
        return t == null ? null : FineType.valueOf(t.toUpperCase());
    }

    @Named("refLoan")
    default Loan refLoan(Long id) {
        if (id == null) return null;
        Loan l = new Loan();
        l.setId(id);
        return l;
    }

    @Named("refMember")
    default Member refMember(Long id) {
        if (id == null) return null;
        Member m = new Member();
        m.setId(id);
        return m;
    }

}
