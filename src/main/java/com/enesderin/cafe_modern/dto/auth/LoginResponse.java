package com.enesderin.cafe_modern.dto.auth;

import lombok.Data;

@Data
public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String role;
}
