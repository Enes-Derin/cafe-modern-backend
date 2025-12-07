package com.enesderin.cafe_modern.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemRequest {
    private String name;
    private String description;
    private Double price;
    private MultipartFile imageFile;
    private Long categoryId;
}
