package com.enesderin.cafe_modern.controller;

import com.enesderin.cafe_modern.dto.request.UserRequest;
import com.enesderin.cafe_modern.dto.response.UserResponse;

public interface UserController {
    RootEntity<UserResponse> getUser(Long id);
    RootEntity<UserResponse> createUser(UserRequest userRequest);
    RootEntity<UserResponse> updateUser(Long id , UserRequest userRequest);

}
