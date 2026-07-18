package az.library.library.service;

import az.library.library.dto.request.CreateCategoryRequest;
import az.library.library.dto.request.UpdateCategoryRequest;
import az.library.library.dto.response.CategoryDetailedResponse;
import az.library.library.dto.response.CategorySummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryDetailedResponse create(CreateCategoryRequest request);

    CategoryDetailedResponse findById(Long id);

    Page<CategorySummaryResponse> findAll(Pageable pageable);

    CategoryDetailedResponse update(Long id, UpdateCategoryRequest request);

    void delete(Long id);

}
