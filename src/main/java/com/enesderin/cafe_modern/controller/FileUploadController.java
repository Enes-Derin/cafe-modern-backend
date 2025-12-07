package com.enesderin.cafe_modern.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadController {


    private String uploadDir = System.getProperty("file.upload-dir");

    @PostMapping("/image")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {

        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        String name = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + "/" + name);

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        String url = "/uploads/" + name;

        return ResponseEntity.ok(url);
    }
}
