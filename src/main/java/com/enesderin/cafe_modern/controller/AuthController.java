package com.enesderin.cafe_modern.controller;

import com.enesderin.cafe_modern.dto.auth.*;

public interface AuthController {
    RootEntity<LoginResponse> login(LoginRequest loginRequest);
    RootEntity<RegisterResponse> register(RegisterRequest registerRequest);
    RootEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
