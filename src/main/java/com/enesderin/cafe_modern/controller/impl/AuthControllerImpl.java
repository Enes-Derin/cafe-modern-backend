package com.enesderin.cafe_modern.controller.impl;


import com.enesderin.cafe_modern.controller.AuthController;
import com.enesderin.cafe_modern.controller.RestBaseController;
import com.enesderin.cafe_modern.controller.RootEntity;
import com.enesderin.cafe_modern.dto.auth.*;
import com.enesderin.cafe_modern.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthControllerImpl extends RestBaseController implements AuthController {

    private AuthService authService;


    @PostMapping("/login")
    @Override
    public RootEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ok(this.authService.login(loginRequest));
    }

    @PostMapping("/register")
    @Override
    public RootEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ok(this.authService.register(registerRequest));
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ok(this.authService.refreshToken(refreshTokenRequest));
    }
}
