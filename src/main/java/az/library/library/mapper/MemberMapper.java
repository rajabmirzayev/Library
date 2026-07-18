package az.library.library.mapper;

import az.library.library.dto.request.CreateMemberRequest;
import az.library.library.dto.request.UpdateMemberRequest;
import az.library.library.dto.response.MemberDetailedResponse;
import az.library.library.dto.response.MemberSummaryResponse;
import az.library.library.entity.Member;
import az.library.library.enums.Gender;
import az.library.library.enums.MemberStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "fines", ignore = true)
    @Mapping(target = "membershipNumber", ignore = true)
    @Mapping(target = "membershipDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "gender", source = "gender", qualifiedByName = "mapGender")
    Member toEntityForCreate(CreateMemberRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "fines", ignore = true)
    @Mapping(target = "membershipNumber", ignore = true)
    @Mapping(target = "membershipDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "gender", source = "gender", qualifiedByName = "mapGender")
    void updateEntity(UpdateMemberRequest request, @MappingTarget Member member);

    @Mapping(target = "gender", expression = "java(member.getGender() != null ? member.getGender().name() : null)")
    @Mapping(target = "activeLoanCount", expression = "java(member.getLoans() != null ? (int) member.getLoans().stream().filter(l -> l.getStatus() == az.library.library.enums.LoanStatus.ACTIVE).count() : 0)")
    @Mapping(target = "pendingFineCount", expression = "java(member.getFines() != null ? (int) member.getFines().stream().filter(f -> f.getStatus() == az.library.library.enums.FineStatus.PENDING).count() : 0)")
    MemberDetailedResponse toDetailedResponse(Member member);

    MemberSummaryResponse toSummaryResponse(Member member);

    @Named("mapGender")
    default Gender mapGender(String g) {
        return g == null ? null : Gender.valueOf(g.toUpperCase());
    }

}
