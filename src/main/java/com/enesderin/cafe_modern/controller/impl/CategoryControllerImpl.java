package com.enesderin.cafe_modern.controller.impl;

import com.enesderin.cafe_modern.controller.CategoryController;
import com.enesderin.cafe_modern.controller.RestBaseController;
import com.enesderin.cafe_modern.controller.RootEntity;
import com.enesderin.cafe_modern.dto.response.CategoryResponse;
import com.enesderin.cafe_modern.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryControllerImpl extends RestBaseController implements CategoryController {

    private CategoryService categoryService;

    @GetMapping
    @Override
    public RootEntity<List<CategoryResponse>> getCategories() {
        return ok(categoryService.getAll());
    }
}
