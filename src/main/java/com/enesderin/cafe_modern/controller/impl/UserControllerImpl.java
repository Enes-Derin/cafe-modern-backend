package com.enesderin.cafe_modern.controller.impl;

import com.enesderin.cafe_modern.controller.RestBaseController;
import com.enesderin.cafe_modern.controller.RootEntity;
import com.enesderin.cafe_modern.controller.UserController;
import com.enesderin.cafe_modern.dto.request.UserRequest;
import com.enesderin.cafe_modern.dto.response.UserResponse;
import com.enesderin.cafe_modern.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class UserControllerImpl extends RestBaseController implements UserController {

    private UserService userService;

    @Override
    @GetMapping("/{id}")
    public RootEntity<UserResponse> getUser(@PathVariable Long id) {
        return ok(this.userService.getUser(id));
    }

    @Override
    @PostMapping
    public RootEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ok(this.userService.createUser(userRequest));
    }

    @Override
    @PutMapping("/update/{id}")
    public RootEntity<UserResponse> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequest userRequest) {
        return ok(this.userService.updateUser(id,userRequest));
    }
}
