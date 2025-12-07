package com.enesderin.cafe_modern.controller.impl;

import com.enesderin.cafe_modern.controller.GalleryController;
import com.enesderin.cafe_modern.controller.MenuItemController;
import com.enesderin.cafe_modern.controller.RestBaseController;
import com.enesderin.cafe_modern.controller.RootEntity;
import com.enesderin.cafe_modern.dto.response.GalleryResponse;
import com.enesderin.cafe_modern.service.GalleryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gallery")
@AllArgsConstructor
public class GalleryImageControllerImpl extends RestBaseController implements GalleryController {

    private GalleryService galleryService;

    @GetMapping
    @Override
    public RootEntity<List<GalleryResponse>> getAll() {
        return ok(galleryService.getAll());
    }
}
