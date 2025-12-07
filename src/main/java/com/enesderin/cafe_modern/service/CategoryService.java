package com.enesderin.cafe_modern.service;

import com.enesderin.cafe_modern.dto.request.CategoryRequest;
import com.enesderin.cafe_modern.dto.response.CategoryResponse;
import com.enesderin.cafe_modern.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse save(CategoryRequest categoryRequest);
    CategoryResponse update(Long id, CategoryRequest categoryRequest);
    void delete(Long id);
}
