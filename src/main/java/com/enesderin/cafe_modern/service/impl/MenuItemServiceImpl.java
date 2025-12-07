package com.enesderin.cafe_modern.service.impl;

import com.enesderin.cafe_modern.dto.request.MenuItemRequest;
import com.enesderin.cafe_modern.dto.response.MenuItemResponse;
import com.enesderin.cafe_modern.exception.BaseException;
import com.enesderin.cafe_modern.exception.ErrorMessage;
import com.enesderin.cafe_modern.exception.MessageType;
import com.enesderin.cafe_modern.model.MenuItem;
import com.enesderin.cafe_modern.repository.CategoryRepository;
import com.enesderin.cafe_modern.repository.MenuItemRepository;
import com.enesderin.cafe_modern.service.MenuItemService;
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
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    private final String UPLOAD_DIR = "uploads/menu-items";

    @Override
    public List<MenuItemResponse> getByCategory(Long categoryId) {

        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, "Category not found")
                ));

        List<MenuItem> menuItems = menuItemRepository.findByCategoryId(categoryId);

        List<MenuItemResponse> responseList = new ArrayList<>();

        for (MenuItem menuItem : menuItems) {
            MenuItemResponse res = new MenuItemResponse();
            res.setId(menuItem.getId());
            res.setName(menuItem.getName());
            res.setDescription(menuItem.getDescription());
            res.setPrice(menuItem.getPrice());
            res.setImageUrl(menuItem.getImageUrl());
            res.setCategoryId(menuItem.getCategory().getId());
            responseList.add(res);
        }

        return responseList;
    }

    @Override
    public MenuItemResponse save(MenuItemRequest menuItemRequest) {
        try {
            // 🔹 Görsel kaydet
            String fileName = null;
            if (menuItemRequest.getImageFile() != null && !menuItemRequest.getImageFile().isEmpty()) {
                fileName = UUID.randomUUID() + "_" + menuItemRequest.getImageFile().getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, menuItemRequest.getImageFile().getBytes());
            }

            // 🔹 Entity oluştur
            MenuItem menuItem = new MenuItem();
            menuItem.setName(menuItemRequest.getName());
            menuItem.setDescription(menuItemRequest.getDescription());
            menuItem.setPrice(menuItemRequest.getPrice());
            menuItem.setCategory(categoryRepository.findById(menuItemRequest.getCategoryId()).orElseThrow());

            if (fileName != null) {
                menuItem.setImageUrl("/uploads/menu-items/" + fileName);
            }

            menuItemRepository.save(menuItem);

            return mapToResponse(menuItem);

        } catch (Exception e) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Image upload error")
            );
        }
    }

    @Override
    public MenuItemResponse update(Long id, MenuItemRequest menuItemRequest) {

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, "Menu item not found")
                ));

        menuItem.setName(menuItemRequest.getName());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setCategory(categoryRepository.findById(menuItemRequest.getCategoryId()).orElseThrow());

        try {
            if (menuItemRequest.getImageFile() != null && !menuItemRequest.getImageFile().isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + menuItemRequest.getImageFile().getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, menuItemRequest.getImageFile().getBytes());
                menuItem.setImageUrl("/uploads/menu-items/" + fileName);
            }
        } catch (Exception e) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Image upload error")
            );
        }

        menuItemRepository.save(menuItem);
        return mapToResponse(menuItem);
    }

    private MenuItemResponse mapToResponse(MenuItem menuItem) {
        MenuItemResponse res = new MenuItemResponse();
        res.setId(menuItem.getId());
        res.setName(menuItem.getName());
        res.setDescription(menuItem.getDescription());
        res.setPrice(menuItem.getPrice());
        res.setImageUrl(menuItem.getImageUrl());
        res.setCategoryId(menuItem.getCategory().getId());
        return res;
    }


    @Override
    public void delete(Long id) {
        menuItemRepository.deleteById(id);
    }
}
