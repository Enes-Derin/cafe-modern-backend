package com.enesderin.cafe_modern.controller;

import com.enesderin.cafe_modern.dto.request.CategoryRequest;
import com.enesderin.cafe_modern.dto.response.CategoryResponse;

public interface AdminCategoryController {
    RootEntity<CategoryResponse> save(CategoryRequest categoryRequest);
    RootEntity<CategoryResponse> update(Long id , CategoryRequest categoryRequest);
    RootEntity<String> delete(Long id);
}
