package com.enesderin.cafe_modern.service.impl;

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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private final String UPLOAD_DIR = "uploads/categories";

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categoryList = this.categoryRepository.findAll();
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setName(category.getName());
            categoryResponse.setImageUrl(category.getImageUrl()); // 🔹 EKLE
            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());

        // 🔹 Görsel kaydet
        if (categoryRequest.getImageFile() != null && !categoryRequest.getImageFile().isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + categoryRequest.getImageFile().getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, categoryRequest.getImageFile().getBytes());
                category.setImageUrl("/uploads/categories/" + fileName);
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Image upload error"));
            }
        }

        categoryRepository.save(category);

        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setImageUrl(category.getImageUrl()); // 🔹 Response’a ekle
        return response;
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, "Category not found")
                ));

        category.setName(req.getName());
        saveImageIfExists(req, category);
        categoryRepository.save(category);

        return toResponse(category);
    }

    private void saveImageIfExists(CategoryRequest req, Category category) {
        if (req.getImageFile() != null && !req.getImageFile().isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + req.getImageFile().getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Files.write(uploadPath.resolve(fileName), req.getImageFile().getBytes());

                category.setImageUrl("/uploads/categories/" + fileName);

            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Image upload error"));
            }
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
