package com.tony.service.impl;

import com.tony.dao.UserDao;
import com.tony.dto.UserLoginRequest;
import com.tony.dto.UserRegisterRequest;
import com.tony.model.User;
import com.tony.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查email是否被註冊
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("The email: {} has been registered", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用BCryptPasswordEncoder加密
        String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查user是否存在
        if (user == null) {
            log.warn("The email: {} has not been registered yet", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        // 使用BCryptPasswordEncoder加密
        String hashedPassword = userLoginRequest.getPassword();
        // 比較密碼
        if (passwordEncoder.matches(hashedPassword, user.getPassword())) {
            return user;
        } else {
            log.warn("email: {} wrong password", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
