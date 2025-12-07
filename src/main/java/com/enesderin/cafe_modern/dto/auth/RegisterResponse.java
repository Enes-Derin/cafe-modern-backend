package com.enesderin.cafe_modern.dto.auth;

import lombok.Data;

@Data
public class RegisterResponse {

    private Long id;
    private String username;
    private String password;
    private String role;
}
