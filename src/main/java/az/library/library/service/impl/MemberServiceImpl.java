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
import az.library.library.service.MemberService;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repo;
    private final MemberMapper mapper;

    @Override
    @Transactional
    public MemberDetailedResponse create(CreateMemberRequest request) {
        if (repo.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email " + request.getEmail() + " already exists");
        Member entity = mapper.toEntityForCreate(request);
        entity.setMembershipNumber("MEM-" + System.currentTimeMillis());
        entity.setMembershipDate(LocalDateTime.now());
        entity.setStatus(MemberStatus.ACTIVE);
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    public MemberDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id)));
    }

    @Override
    public Page<MemberSummaryResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toSummaryResponse);
    }

    @Override
    @Transactional
    public MemberDetailedResponse update(Long id, UpdateMemberRequest request) {
        Member entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member", id));
        if (request.getEmail() != null && !entity.getEmail().equals(request.getEmail()) && repo.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email " + request.getEmail() + " already exists");
        mapper.updateEntity(request, entity);
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member", id)));
    }
}
