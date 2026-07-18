package az.library.library.service.impl;

import az.library.library.dto.request.CreatePublisherRequest;
import az.library.library.dto.request.UpdatePublisherRequest;
import az.library.library.dto.response.PublisherDetailedResponse;
import az.library.library.dto.response.PublisherSummaryResponse;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.PublisherMapper;
import az.library.library.repository.PublisherRepository;
import az.library.library.service.PublisherService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository repo;
    private final PublisherMapper mapper;

    @Override
    @Transactional
    public PublisherDetailedResponse create(CreatePublisherRequest request) {
        return mapper.toDetailedResponse(repo.save(mapper.toEntityForCreate(request)));
    }

    @Override
    public PublisherDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", id)));
    }

    @Override
    public List<PublisherSummaryResponse> findAll() {
        return repo.findAll().stream().map(mapper::toSummaryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PublisherDetailedResponse update(Long id, UpdatePublisherRequest request) {
        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher", id));
        mapper.updateEntity(request, entity);
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher", id)));
    }
}
