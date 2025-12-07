package com.enesderin.cafe_modern.controller.impl;

import com.enesderin.cafe_modern.controller.AdminMenuItemController;
import com.enesderin.cafe_modern.controller.MenuItemController;
import com.enesderin.cafe_modern.controller.RestBaseController;
import com.enesderin.cafe_modern.controller.RootEntity;
import com.enesderin.cafe_modern.dto.request.MenuItemRequest;
import com.enesderin.cafe_modern.dto.response.MenuItemResponse;
import com.enesderin.cafe_modern.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/menu-item")
@AllArgsConstructor
public class AdminMenuItemControllerImpl extends RestBaseController implements AdminMenuItemController {

    private final MenuItemService menuItemService;

    // --- CREATE ---
    @PostMapping(consumes = "multipart/form-data")
    @Override
    public RootEntity<MenuItemResponse> add(@ModelAttribute MenuItemRequest menuItemRequest) {
        return ok(menuItemService.save(menuItemRequest));
    }

    // --- UPDATE ---
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @Override
    public RootEntity<MenuItemResponse> update(
            @PathVariable Long id,
            @ModelAttribute MenuItemRequest menuItemRequest
    ) {
        return ok(menuItemService.update(id, menuItemRequest));
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    @Override
    public RootEntity<String> delete(@PathVariable Long id) {
        menuItemService.delete(id);
        return ok("Deleted");
    }
}

