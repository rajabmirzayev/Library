package az.library.library.service;

import az.library.library.dto.request.CreateMemberRequest;
import az.library.library.dto.request.UpdateMemberRequest;
import az.library.library.dto.response.MemberDetailedResponse;
import az.library.library.dto.response.MemberSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    MemberDetailedResponse create(CreateMemberRequest request);

    MemberDetailedResponse findById(Long id);

    Page<MemberSummaryResponse> findAll(Pageable pageable);

    MemberDetailedResponse update(Long id, UpdateMemberRequest request);

    void delete(Long id);

}
