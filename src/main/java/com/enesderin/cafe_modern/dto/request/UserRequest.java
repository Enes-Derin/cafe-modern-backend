package com.enesderin.cafe_modern.dto.request;

import com.enesderin.cafe_modern.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Role role;
}
