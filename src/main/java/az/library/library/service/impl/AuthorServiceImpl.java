package az.library.library.service.impl;

import az.library.library.dto.request.CreateAuthorRequest;
import az.library.library.dto.request.UpdateAuthorRequest;
import az.library.library.dto.response.AuthorDetailedResponse;
import az.library.library.dto.response.AuthorSummaryResponse;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.AuthorMapper;
import az.library.library.repository.AuthorRepository;
import az.library.library.service.AuthorService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repo;
    private final AuthorMapper mapper;

    @Override
    @Transactional
    public AuthorDetailedResponse create(CreateAuthorRequest request) {
        var entity = mapper.toEntityForCreate(request);
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    public AuthorDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", id)));
    }

    @Override
    public Page<AuthorSummaryResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toSummaryResponse);
    }

    @Override
    @Transactional
    public AuthorDetailedResponse update(Long id, UpdateAuthorRequest request) {
        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", id));
        mapper.updateEntity(request, entity);
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author", id)));
    }
}
