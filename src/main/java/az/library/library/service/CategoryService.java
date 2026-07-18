package az.library.library.service;

import az.library.library.dto.request.CreateCategoryRequest;
import az.library.library.dto.request.UpdateCategoryRequest;
import az.library.library.dto.response.CategoryDetailedResponse;
import az.library.library.dto.response.CategorySummaryResponse;

import java.util.List;

public interface CategoryService {

    CategoryDetailedResponse create(CreateCategoryRequest request);

    CategoryDetailedResponse findById(Long id);

    List<CategorySummaryResponse> findAll();

    CategoryDetailedResponse update(Long id, UpdateCategoryRequest request);

    void delete(Long id);

}
