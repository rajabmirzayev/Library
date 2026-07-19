package az.library.library.service.impl;

import az.library.library.dto.request.CreateMemberRequest;
import az.library.library.dto.request.UpdateMemberRequest;
import az.library.library.dto.response.MemberDetailedResponse;
import az.library.library.dto.response.MemberSummaryResponse;
import az.library.library.entity.Member;
import az.library.library.enums.MemberStatus;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.MemberMapper;
import az.library.library.repository.MemberRepository;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository repo;

    @Mock
    private MemberMapper mapper;

    @InjectMocks
    private MemberServiceImpl service;

    @Test
    void Given_ValidRequest_When_Create_Then_ReturnsDetailedResponse() {
        CreateMemberRequest request = new CreateMemberRequest();
        request.setFirstName("Əli");
        request.setLastName("Həsənov");
        request.setEmail("ali@library.az");

        Member entity = Member.builder().firstName("Əli").lastName("Həsənov").email("ali@library.az").build();
        MemberDetailedResponse response = new MemberDetailedResponse();
        response.setId(1L);

        given(repo.existsByEmail("ali@library.az")).willReturn(false);
        given(mapper.toEntityForCreate(request)).willReturn(entity);
        given(repo.save(any(Member.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        MemberDetailedResponse result = service.create(request);

        assertThat(result).isNotNull();
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getMembershipNumber()).isNotNull();
        assertThat(captor.getValue().getMembershipDate()).isNotNull();
        assertThat(captor.getValue().getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void Given_DuplicateEmail_When_Create_Then_ThrowsIllegalArgumentException() {
        CreateMemberRequest request = new CreateMemberRequest();
        request.setEmail("ali@library.az");

        given(repo.existsByEmail("ali@library.az")).willReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void Given_ExistingId_When_FindById_Then_ReturnsDetailedResponse() {
        Long id = 1L;
        Member entity = Member.builder().id(id).firstName("Əli").build();
        MemberDetailedResponse response = new MemberDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        MemberDetailedResponse result = service.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void Given_NonExistingId_When_FindById_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Member");
    }

    @Test
    void Given_Pageable_When_FindAll_Then_ReturnsPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Member entity = Member.builder().id(1L).firstName("Əli").build();
        MemberSummaryResponse summary = new MemberSummaryResponse();
        summary.setId(1L);
        Page<Member> page = new PageImpl<>(List.of(entity), pageable, 1);

        given(repo.findAll(pageable)).willReturn(page);
        given(mapper.toSummaryResponse(entity)).willReturn(summary);

        Page<MemberSummaryResponse> result = service.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void Given_SameEmail_When_Update_Then_UpdatesSuccessfully() {
        Long id = 1L;
        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setEmail("ali@library.az");

        Member entity = Member.builder().id(id).email("ali@library.az").build();
        MemberDetailedResponse response = new MemberDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.save(any(Member.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        MemberDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
        verify(mapper).updateEntity(request, entity);
    }

    @Test
    void Given_NewEmail_When_Update_Then_UpdatesSuccessfully() {
        Long id = 1L;
        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setEmail("yeni@library.az");

        Member entity = Member.builder().id(id).email("ali@library.az").build();
        MemberDetailedResponse response = new MemberDetailedResponse();
        response.setId(id);

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.existsByEmail("yeni@library.az")).willReturn(false);
        given(repo.save(any(Member.class))).willReturn(entity);
        given(mapper.toDetailedResponse(entity)).willReturn(response);

        MemberDetailedResponse result = service.update(id, request);

        assertThat(result).isNotNull();
    }

    @Test
    void Given_DuplicateNewEmail_When_Update_Then_ThrowsIllegalArgumentException() {
        Long id = 1L;
        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setEmail("duplicated@library.az");

        Member entity = Member.builder().id(id).email("ali@library.az").build();

        given(repo.findById(id)).willReturn(Optional.of(entity));
        given(repo.existsByEmail("duplicated@library.az")).willReturn(true);

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void Given_NonExistingId_When_Update_Then_ThrowsResourceNotFoundException() {
        Long id = 999L;
        UpdateMemberRequest request = new UpdateMemberRequest();
        given(repo.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Member");
    }

    @Test
    void Given_ExistingId_When_Delete_Then_DeletesSuccessfully() {
        Long id = 1L;
        Member entity = Member.builder().id(id).build();
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
                .hasMessageContaining("Member");
    }
}
