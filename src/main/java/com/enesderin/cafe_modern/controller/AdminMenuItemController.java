package com.enesderin.cafe_modern.controller;

import com.enesderin.cafe_modern.dto.request.MenuItemRequest;
import com.enesderin.cafe_modern.dto.response.MenuItemResponse;

public interface AdminMenuItemController {
    RootEntity<MenuItemResponse> add(MenuItemRequest menuItemRequest);
    RootEntity<MenuItemResponse> update(Long id, MenuItemRequest menuItemRequest);
    RootEntity<String> delete(Long id);

}
