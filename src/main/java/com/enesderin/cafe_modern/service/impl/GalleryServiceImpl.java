package com.enesderin.cafe_modern.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.enesderin.cafe_modern.dto.request.GalleryRequest;
import com.enesderin.cafe_modern.dto.response.GalleryResponse;
import com.enesderin.cafe_modern.exception.BaseException;
import com.enesderin.cafe_modern.exception.ErrorMessage;
import com.enesderin.cafe_modern.exception.MessageType;
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
import java.util.Map;
import java.util.UUID;
@Service
@AllArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryImageRepository galleryImageRepository;

    private Cloudinary cloudinary;

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
           String imageUrl = uploadToCloudinary(file);
           GalleryImage galleryImage = new GalleryImage();
           galleryImage.setImageUrl(imageUrl);
           galleryImageRepository.save(galleryImage);
           return new GalleryResponse(galleryImage.getId(), imageUrl);

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,""));
        }
    }


    public String uploadToCloudinary(MultipartFile file) {
        try{
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Map upload = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "public_id","cafe-modern/gallery/"+fileName,
                            "overwrite",true
                    )
            );
            return upload.get("secure_url").toString();
        }catch(Exception e){
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,""));
        }
    }


    @Override
    public Long delete(Long id) {
        galleryImageRepository.deleteById(id);
        return id;
    }
}

