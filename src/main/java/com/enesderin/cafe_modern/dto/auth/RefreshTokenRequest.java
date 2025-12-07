package com.enesderin.cafe_modern.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotNull
    private String refreshToken;
}
