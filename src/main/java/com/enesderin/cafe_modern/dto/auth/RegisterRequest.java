package com.enesderin.cafe_modern.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
