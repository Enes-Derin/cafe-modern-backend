package com.enesderin.cafe_modern.service.impl;

import com.enesderin.cafe_modern.dto.auth.*;
import com.enesderin.cafe_modern.exception.BaseException;
import com.enesderin.cafe_modern.exception.ErrorMessage;
import com.enesderin.cafe_modern.exception.MessageType;
import com.enesderin.cafe_modern.model.RefreshToken;
import com.enesderin.cafe_modern.model.Role;
import com.enesderin.cafe_modern.model.User;
import com.enesderin.cafe_modern.repository.RefreshTokenRepository;
import com.enesderin.cafe_modern.repository.UserRepository;
import com.enesderin.cafe_modern.security.JwtService;
import com.enesderin.cafe_modern.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;
    private AuthenticationProvider authenticationProvider;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtService jwtService;

    private User createUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return user;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User savedUser = this.userRepository.save(createUser(registerRequest));
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedUser.getId());
        registerResponse.setUsername(savedUser.getUsername());
        registerResponse.setPassword(savedUser.getPassword());
        registerResponse.setRole(Role.ADMIN.name());
        return registerResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            authenticationProvider.authenticate(authenticationToken);
            Optional<User> optional = this.userRepository.findByUsername(loginRequest.getUsername());
            String accessToken = jwtService.createToken(optional.get());
            RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(optional.get()));
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(accessToken);
            loginResponse.setRefreshToken(refreshToken.getRefreshToken());
            loginResponse.setRole(optional.get().getRole().name());
            return loginResponse;
        }catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
        }
    }

    public boolean isValidRefreshToken(Date expiryDate) {
        return new Date().before(expiryDate);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> optional = this.refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken());
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, refreshTokenRequest.getRefreshToken()));
        }
        if (!isValidRefreshToken(optional.get().getExpiryDate())) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED, refreshTokenRequest.getRefreshToken()));
        }
        User user = optional.get().getUser();
        String accessToken = jwtService.createToken(user);
        RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(user));
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        refreshTokenResponse.setAccessToken(accessToken);
        refreshTokenResponse.setRefreshToken(refreshToken.getRefreshToken());
        return refreshTokenResponse;
    }
}
