package com.enesderin.cafe_modern.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    private Cloudinary cloudinary;

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
       MenuItem menuItem = new MenuItem();
       menuItem.setName(menuItemRequest.getName());
       menuItem.setDescription(menuItemRequest.getDescription());
       menuItem.setPrice(menuItemRequest.getPrice());
       menuItem.setCategory(categoryRepository.findById(menuItemRequest.getCategoryId()).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, "Category not found"))));
        if (menuItemRequest.getImageFile() != null && !menuItemRequest.getImageFile().isEmpty()) {
            menuItem.setImageUrl(uploadToCloudinary(menuItemRequest));
        }
        MenuItem saved = this.menuItemRepository.save(menuItem);
        MenuItemResponse itemResponse = new MenuItemResponse();
        itemResponse.setId(saved.getId());
        itemResponse.setName(saved.getName());
        itemResponse.setDescription(saved.getDescription());
        itemResponse.setPrice(saved.getPrice());
        itemResponse.setCategoryId(saved.getCategory().getId());
        itemResponse.setImageUrl(saved.getImageUrl());
        return itemResponse;
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
                menuItem.setImageUrl(uploadToCloudinary(menuItemRequest));
            }
        } catch (Exception e) {
            throw new BaseException(
                    new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Image upload error")
            );
        }

        MenuItem saved = menuItemRepository.save(menuItem);
        MenuItemResponse itemResponse = new MenuItemResponse();
        itemResponse.setId(saved.getId());
        itemResponse.setName(saved.getName());
        itemResponse.setDescription(saved.getDescription());
        itemResponse.setPrice(saved.getPrice());
        itemResponse.setCategoryId(saved.getCategory().getId());
        itemResponse.setImageUrl(saved.getImageUrl());
        return itemResponse;

    }


    private String uploadToCloudinary(MenuItemRequest menuItemRequest) {
        try{
            String fileName = UUID.randomUUID().toString() + "_" + menuItemRequest.getImageFile().getOriginalFilename();
            Map upload = cloudinary.uploader().upload(
                    menuItemRequest.getImageFile().getBytes(),
                    ObjectUtils.asMap(
                            "public_id","cafe-modern/menu-items/" + fileName,
                            "overwrite",true)
            );
            return upload.get("secure_url").toString();
        }catch (IOException e){
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Image upload error"));
        }
    }


    @Override
    public void delete(Long id) {
        menuItemRepository.deleteById(id);
    }
}
