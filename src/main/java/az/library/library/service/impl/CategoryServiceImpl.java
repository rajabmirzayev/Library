package az.library.library.service.impl;

import az.library.library.dto.request.CreateCategoryRequest;
import az.library.library.dto.request.UpdateCategoryRequest;
import az.library.library.dto.response.CategoryDetailedResponse;
import az.library.library.dto.response.CategorySummaryResponse;
import az.library.library.entity.Category;
import az.library.library.exception.ResourceNotFoundException;
import az.library.library.mapper.CategoryMapper;
import az.library.library.repository.CategoryRepository;
import az.library.library.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repo;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryDetailedResponse create(CreateCategoryRequest request) {
        var entity = mapper.toEntityForCreate(request);
        if (request.getParentId() != null)
            entity.setParent(repo.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", request.getParentId())));
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    public CategoryDetailedResponse findById(Long id) {
        return mapper.toDetailedResponse(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id)));
    }

    @Override
    public List<CategorySummaryResponse> findAll() {
        return repo.findAll().stream().map(mapper::toSummaryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDetailedResponse update(Long id, UpdateCategoryRequest request) {
        var entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", id));
        mapper.updateEntity(request, entity);
        if (request.getParentId() != null)
            entity.setParent(repo.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", request.getParentId())));
        return mapper.toDetailedResponse(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", id)));
    }
}
