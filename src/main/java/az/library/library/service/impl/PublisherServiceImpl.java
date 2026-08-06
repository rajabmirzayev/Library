package az.library.library.service.impl;

import az.library.library.dto.request.CreatePublisherRequest;
import az.library.library.dto.request.UpdatePublisherRequest;
import az.library.library.dto.response.PublisherDetailedResponse;
import az.library.library.dto.response.PublisherSummaryResponse;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.PublisherMapper;
import az.library.library.repository.PublisherRepository;
import az.library.library.service.PublisherService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository repo;
    private final PublisherMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PublisherDetailedResponse create(CreatePublisherRequest request) {
        return mapper.toDetailedResponse(repo.save(mapper.toEntityForCreate(request)));
    }

    @Override
    public PublisherDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", id)));
    }

    @Override
    public Page<PublisherSummaryResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toSummaryResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PublisherDetailedResponse update(Long id, UpdatePublisherRequest request) {
        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher", id));
        mapper.updateEntity(request, entity);
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher", id)));
    }
}
