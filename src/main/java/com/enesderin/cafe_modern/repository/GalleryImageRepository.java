package com.enesderin.cafe_modern.repository;

import com.enesderin.cafe_modern.model.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryImageRepository extends JpaRepository<GalleryImage,Long> {
}
