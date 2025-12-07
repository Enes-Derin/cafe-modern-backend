package com.enesderin.cafe_modern.controller;

import com.enesderin.cafe_modern.dto.response.MenuItemResponse;

import java.util.List;

public interface MenuItemController {
    RootEntity<List<MenuItemResponse>> getByCategory(Long id);
}
