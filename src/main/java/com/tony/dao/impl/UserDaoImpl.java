//package com.tony.dao.impl;
//
//import com.tony.dto.UserRegisterRequest;
//import com.tony.model.User;
//import com.tony.rowmapper.UserRowMapper;
//import com.tony.dao.UserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class UserDaoImpl implements UserDao {
//
//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @Override
//    public User getUserById(Integer userId) {
//        String sql = "SELECT user_id, email, password, created_date, last_modified_date " +
//                "FROM user WHERE user_id = :userId";
//
//        return getUser(sql, "userId", userId);
//    }
//
//    @Override
//    public User getUserByEmail(String email) {
//        String sql = "SELECT user_id, email, password, created_date, last_modified_date " +
//                "FROM user WHERE email = :email";
//
//        return getUser(sql, "email", email);
//    }
//
//    private User getUser(String sql, String key, Object value) {
//        Map<String, Object> map = new HashMap<>();
//        map.put(key, value);
//
//        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
//
//        if (userList.isEmpty()) {
//            return null;
//        }
//
//        return userList.get(0);
//    }
//
//    @Override
//    public Integer createUser(UserRegisterRequest userRegisterRequest) {
//        String sql = "INSERT INTO user(email, password) VALUES (:email, :password)";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("email", userRegisterRequest.getEmail());
//        map.put("password", userRegisterRequest.getPassword());
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
//
//        Integer userId = keyHolder.getKey().intValue();
//
//        return userId;
//    }
//}
