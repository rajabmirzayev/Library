package az.library.library.service;

import az.library.library.dto.request.CreateMemberRequest;
import az.library.library.dto.request.UpdateMemberRequest;
import az.library.library.dto.response.MemberDetailedResponse;
import az.library.library.dto.response.MemberSummaryResponse;

import java.util.List;

public interface MemberService {

    MemberDetailedResponse create(CreateMemberRequest request);

    MemberDetailedResponse findById(Long id);

    List<MemberSummaryResponse> findAll();

    MemberDetailedResponse update(Long id, UpdateMemberRequest request);

    void delete(Long id);

}
