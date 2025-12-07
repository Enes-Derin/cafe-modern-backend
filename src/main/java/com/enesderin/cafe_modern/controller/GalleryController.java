package com.enesderin.cafe_modern.controller;

import com.enesderin.cafe_modern.dto.response.GalleryResponse;

import java.util.List;

public interface GalleryController {
    RootEntity<List<GalleryResponse>> getAll();
}
