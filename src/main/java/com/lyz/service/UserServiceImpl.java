package com.lyz.service;

import com.lyz.mapper.UserMapper;
import com.lyz.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    public void changeIsUpload(Integer id) {
        userMapper.changeIsUpload(id);
    }

    public void registerNewUser(String realname, Integer classId, String username, String password, String email) {
        userMapper.registerNewUser(realname, classId, username, password, email);
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    public User getUserByClassId(Integer classId) {
        return userMapper.getUserByClassId(classId);
    }

    public User getUserByUserName_RealName_Email(String username, String realname, String email) {
        return userMapper.getUserByUserName_RealName_Email(username, realname, email);
    }

    public void changePassword(Integer id, String newpassword) {
        userMapper.changePassword(id, newpassword);
    }

    public void changeUserEmail(Integer id, String email) {
        userMapper.changeUserEmail(id, email);
    }

}
