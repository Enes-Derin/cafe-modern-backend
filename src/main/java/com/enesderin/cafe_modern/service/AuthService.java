package com.enesderin.cafe_modern.service;

import com.enesderin.cafe_modern.dto.auth.*;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
