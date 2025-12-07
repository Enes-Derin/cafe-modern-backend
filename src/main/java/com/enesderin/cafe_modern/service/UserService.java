package com.enesderin.cafe_modern.service;

import com.enesderin.cafe_modern.dto.request.UserRequest;
import com.enesderin.cafe_modern.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUser(Long id);
    UserResponse updateUser(Long id, UserRequest userRequest);
}
