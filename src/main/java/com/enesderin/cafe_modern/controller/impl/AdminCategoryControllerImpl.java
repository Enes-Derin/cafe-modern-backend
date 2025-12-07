package com.enesderin.cafe_modern.controller.impl;

import com.enesderin.cafe_modern.controller.AdminCategoryController;
import com.enesderin.cafe_modern.controller.RestBaseController;
import com.enesderin.cafe_modern.controller.RootEntity;
import com.enesderin.cafe_modern.dto.request.CategoryRequest;
import com.enesderin.cafe_modern.dto.response.CategoryResponse;
import com.enesderin.cafe_modern.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@AllArgsConstructor
public class AdminCategoryControllerImpl extends RestBaseController implements AdminCategoryController {

    private CategoryService categoryService;

    @PostMapping()
    @Override
    public RootEntity<CategoryResponse> save(@ModelAttribute CategoryRequest categoryRequest) {
        return ok(categoryService.save(categoryRequest));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<CategoryResponse> update(
            @PathVariable Long id,
            @ModelAttribute CategoryRequest categoryRequest
    ) {
        return ok(categoryService.update(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    @Override
    public RootEntity<String> delete(@PathVariable Long id) {
        this.categoryService.delete(id);
        return ok("Deleted");
    }
}
