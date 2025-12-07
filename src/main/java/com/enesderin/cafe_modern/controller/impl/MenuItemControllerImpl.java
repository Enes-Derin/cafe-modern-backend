package com.enesderin.cafe_modern.controller.impl;

import com.enesderin.cafe_modern.controller.MenuItemController;
import com.enesderin.cafe_modern.controller.RestBaseController;
import com.enesderin.cafe_modern.controller.RootEntity;
import com.enesderin.cafe_modern.dto.response.MenuItemResponse;
import com.enesderin.cafe_modern.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu-item")
@AllArgsConstructor
public class MenuItemControllerImpl extends RestBaseController implements MenuItemController {

    private MenuItemService menuItemService;

    @GetMapping("/{id}")
    @Override
    public RootEntity<List<MenuItemResponse>> getByCategory(@PathVariable Long id) {
        return ok(this.menuItemService.getByCategory(id));
    }
}
