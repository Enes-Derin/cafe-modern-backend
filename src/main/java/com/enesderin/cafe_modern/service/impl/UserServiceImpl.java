package com.enesderin.cafe_modern.service.impl;

import com.enesderin.cafe_modern.dto.request.UserRequest;
import com.enesderin.cafe_modern.dto.response.UserResponse;
import com.enesderin.cafe_modern.exception.BaseException;
import com.enesderin.cafe_modern.exception.ErrorMessage;
import com.enesderin.cafe_modern.exception.MessageType;
import com.enesderin.cafe_modern.model.User;
import com.enesderin.cafe_modern.repository.UserRepository;
import com.enesderin.cafe_modern.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(userRequest.getUsername());
        userResponse.setPassword(userRequest.getPassword());
        userResponse.setRole(userRequest.getRole());
        return userResponse;
    }

    @Override
    public UserResponse getUser(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(optionalUser.get().getUsername());
            userResponse.setPassword(optionalUser.get().getPassword());
            userResponse.setRole(optionalUser.get().getRole());
            return userResponse;
        }
        throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND,"Username not found"));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setRole(userRequest.getRole());
            userRepository.save(user);
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(userRequest.getUsername());
            userResponse.setPassword(userRequest.getPassword());
            userResponse.setRole(userRequest.getRole());
            return userResponse;
        }
        throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND,"Username not found"));
    }
}
