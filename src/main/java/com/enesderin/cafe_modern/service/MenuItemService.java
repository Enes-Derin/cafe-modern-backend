package com.enesderin.cafe_modern.service;

import com.enesderin.cafe_modern.dto.request.MenuItemRequest;
import com.enesderin.cafe_modern.dto.response.MenuItemResponse;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> getByCategory(Long categoryId);
    MenuItemResponse save(MenuItemRequest menuItemRequest);
    MenuItemResponse update(Long id, MenuItemRequest menuItemRequest);
    void delete(Long id);
}
