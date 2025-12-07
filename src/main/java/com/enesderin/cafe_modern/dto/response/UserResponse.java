package com.enesderin.cafe_modern.dto.response;

import com.enesderin.cafe_modern.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
