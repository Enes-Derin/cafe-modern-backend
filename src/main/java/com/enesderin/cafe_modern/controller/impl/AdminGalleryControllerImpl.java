package com.enesderin.cafe_modern.controller.impl;

import com.enesderin.cafe_modern.controller.AdminGalleryController;
import com.enesderin.cafe_modern.controller.RestBaseController;
import com.enesderin.cafe_modern.controller.RootEntity;
import com.enesderin.cafe_modern.dto.request.GalleryRequest;
import com.enesderin.cafe_modern.dto.response.GalleryResponse;
import com.enesderin.cafe_modern.service.GalleryService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/gallery")
@AllArgsConstructor
public class AdminGalleryControllerImpl extends RestBaseController implements AdminGalleryController {

    private final GalleryService galleryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public RootEntity<GalleryResponse> add(@RequestPart("file") MultipartFile file) {
        GalleryResponse response = galleryService.save(file);
        return ok(response);
    }

    @DeleteMapping("/{id}")
    @Override
    public RootEntity<Long> delete(@PathVariable Long id) {
        galleryService.delete(id);
        return ok(id);
    }
}
