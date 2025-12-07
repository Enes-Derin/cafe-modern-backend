package com.enesderin.cafe_modern.controller;

import com.enesderin.cafe_modern.dto.request.CategoryRequest;
import com.enesderin.cafe_modern.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryController {
    RootEntity<List<CategoryResponse>> getCategories();
}
