package com.enesderin.cafe_modern.controller;

import com.enesderin.cafe_modern.dto.request.GalleryRequest;
import com.enesderin.cafe_modern.dto.response.GalleryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminGalleryController {
    RootEntity<GalleryResponse> add(MultipartFile multipartFile) throws IOException;
    RootEntity<Long> delete(Long id);
}
