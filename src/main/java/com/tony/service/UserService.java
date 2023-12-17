package com.tony.service;

import com.tony.dto.UserLoginRequest;
import com.tony.dto.UserRegisterRequest;
import com.tony.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);
}
