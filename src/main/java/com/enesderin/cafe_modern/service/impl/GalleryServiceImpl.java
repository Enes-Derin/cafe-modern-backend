package com.enesderin.cafe_modern.service.impl;

import com.enesderin.cafe_modern.dto.request.GalleryRequest;
import com.enesderin.cafe_modern.dto.response.GalleryResponse;
import com.enesderin.cafe_modern.model.GalleryImage;
import com.enesderin.cafe_modern.repository.GalleryImageRepository;
import com.enesderin.cafe_modern.service.GalleryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryImageRepository galleryImageRepository;

    private final String UPLOAD_DIR = "uploads/gallery";

    @Override
    public List<GalleryResponse> getAll() {
        List<GalleryImage> list = galleryImageRepository.findAll();
        List<GalleryResponse> res = new ArrayList<>();
        for (GalleryImage g : list) {
            res.add(new GalleryResponse(g.getId(), g.getImageUrl()));
        }
        return res;
    }

    @Override
    public GalleryResponse save(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            GalleryImage image = new GalleryImage();
            image.setImageUrl("/uploads/gallery/" + fileName);
            galleryImageRepository.save(image);

            return new GalleryResponse(image.getId(), image.getImageUrl());

        } catch (Exception e) {
            throw new RuntimeException("Gallery upload failed: " + e.getMessage());
        }
    }

    @Override
    public Long delete(Long id) {
        galleryImageRepository.deleteById(id);
        return id;
    }
}

