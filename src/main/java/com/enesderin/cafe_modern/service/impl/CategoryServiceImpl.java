package com.enesderin.cafe_modern.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.enesderin.cafe_modern.dto.request.CategoryRequest;
import com.enesderin.cafe_modern.dto.response.CategoryResponse;
import com.enesderin.cafe_modern.exception.BaseException;
import com.enesderin.cafe_modern.exception.ErrorMessage;
import com.enesderin.cafe_modern.exception.MessageType;
import com.enesderin.cafe_modern.model.Category;
import com.enesderin.cafe_modern.repository.CategoryRepository;
import com.enesderin.cafe_modern.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinary;

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categoryList = this.categoryRepository.findAll();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setName(category.getName());
            categoryResponse.setImageUrl(category.getImageUrl());
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());

        if (categoryRequest.getImageFile() != null && !categoryRequest.getImageFile().isEmpty()) {
            category.setImageUrl(uploadToCloudinary(categoryRequest));
        }

        categoryRepository.save(category);
        return toResponse(category);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, "Category not found")
                ));

        category.setName(req.getName());

        if (req.getImageFile() != null && !req.getImageFile().isEmpty()) {
            category.setImageUrl(uploadToCloudinary(req));
        }

        categoryRepository.save(category);
        return toResponse(category);
    }

    private String uploadToCloudinary(CategoryRequest req) {
        try {
            String fileName = UUID.randomUUID().toString();
            Map upload = cloudinary.uploader().upload(
                    req.getImageFile().getBytes(),
                    ObjectUtils.asMap(
                            "public_id", "cafe-modern/categories/" + fileName,
                            "overwrite", true
                    )
            );

            return upload.get("secure_url").toString();

        } catch (IOException e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,
                    "Image upload error: " + e.getMessage()));
        }
    }

    private CategoryResponse toResponse(Category cat) {
        return new CategoryResponse(
                cat.getId(),
                cat.getName(),
                cat.getImageUrl()
        );
    }

    @Override
    public void delete(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
