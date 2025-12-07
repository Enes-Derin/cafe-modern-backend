package com.enesderin.cafe_modern.service;

import com.enesderin.cafe_modern.dto.request.GalleryRequest;
import com.enesderin.cafe_modern.dto.response.GalleryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GalleryService {
    List<GalleryResponse> getAll();
    GalleryResponse save(MultipartFile file);
    Long delete(Long id);
}
