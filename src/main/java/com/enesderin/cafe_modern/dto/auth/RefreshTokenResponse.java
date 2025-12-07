package com.enesderin.cafe_modern.dto.auth;

import lombok.Data;

@Data
public class RefreshTokenResponse {

    private String accessToken;
    private String refreshToken;
}
